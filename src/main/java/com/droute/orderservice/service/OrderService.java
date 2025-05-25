package com.droute.orderservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.droute.orderservice.dto.request.OrderRequestDto;
import com.droute.orderservice.dto.request.PaymentRequestDto;
import com.droute.orderservice.dto.response.OrderResponseDto;
import com.droute.orderservice.dto.response.PaymentResponseDto;
import com.droute.orderservice.entity.Order;
import com.droute.orderservice.enums.OrderStatus;
import com.droute.orderservice.feign.client.DriverServiceClient;
import com.droute.orderservice.feign.client.UserServiceClient;
import com.droute.orderservice.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class OrderService {

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private  OrderRepository orderRepository;
    
    @Autowired
    private  PaymentService paymentService;

    @Autowired
    private DriverServiceClient driverServiceClient;

    
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
        // Validate the order request
        if(!driverServiceClient.journeyExistsById(orderRequest.getJourneyId()).getData()) {
            throw new EntityNotFoundException("Journey does not exist with id: " + orderRequest.getJourneyId());
        }
        if (!userServiceClient.courierExistsById(orderRequest.getCourierId()).getData()) {
            throw new EntityNotFoundException("Courier does not exist with id: " + orderRequest.getCourierId());
            
        }
        Order order = Order.builder()
                .courierId(orderRequest.getCourierId())
                .journeyId(orderRequest.getJourneyId())
                .orderStatus(OrderStatus.PENDING)
                .estimatedFare(null != orderRequest.getEstimatedFare() ? orderRequest.getEstimatedFare() : 0.0)
                .offeredFare(null != orderRequest.getOfferedFare() ? orderRequest.getOfferedFare() : 0.0)
                .build();

        Order savedOrder = orderRepository.save(order);

        // If payment information is provided in the order request
        if (orderRequest.getPaymentRequestDto() != null) {
            PaymentRequestDto paymentRequest = orderRequest.getPaymentRequestDto();
            paymentRequest.setOrderId(savedOrder.getId());
            paymentService.processPayment(paymentRequest);
        }

        return mapToOrderResponse(savedOrder);
    }


    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return mapToOrderResponse(order);
    }

    
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

   
    @Transactional
    public OrderResponseDto updateOrder(Long id, OrderRequestDto orderRequest) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        existingOrder.setCourierId(orderRequest.getCourierId());
        existingOrder.setJourneyId(orderRequest.getJourneyId());
        existingOrder.setOrderStatus(orderRequest.getOrderStatus());
        existingOrder.setEstimatedFare(orderRequest.getEstimatedFare() != null ? orderRequest.getEstimatedFare() : 0.0);
        existingOrder.setOfferedFare(orderRequest.getOfferedFare() != null ? orderRequest.getOfferedFare() : 0.0);
        Order updatedOrder = orderRepository.save(existingOrder);
        return mapToOrderResponse(updatedOrder);
    }


    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepository.delete(order);
    }


    @Transactional
    public OrderResponseDto updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        order.setOrderStatus(status);
        Order updatedOrder = orderRepository.save(order);
        
        return mapToOrderResponse(updatedOrder);
    }

    private OrderResponseDto mapToOrderResponse(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .courierId(order.getCourierId())
                .journeyId(order.getJourneyId())
                .orderStatus(order.getOrderStatus())
                .estimatedFare(order.getEstimatedFare())
                .offeredFare(order.getOfferedFare())
                .payment(order.getPayment() != null ? 
                    PaymentResponseDto.builder()
                        .id(order.getPayment().getId())
                        .amount(order.getPayment().getAmount())
                        .paymentMethod(order.getPayment().getPaymentMethod())
                        .status(order.getPayment().getStatus())
                        .transactionId(order.getPayment().getTransactionId())
                        .build() 
                    : null)
                .build();
    }
}