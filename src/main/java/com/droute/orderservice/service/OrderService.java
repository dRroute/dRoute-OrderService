package com.droute.orderservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.droute.orderservice.dto.request.CourierDetailsRequestDto;
import com.droute.orderservice.dto.request.NewOrderRequestDto;
import com.droute.orderservice.dto.response.AllJourneysOrderResponseDto;
import com.droute.orderservice.dto.response.CourierDetailResponseDto;
import com.droute.orderservice.dto.response.FilteredJourneyDetailsResponseDto;
import com.droute.orderservice.dto.response.OrderDetailsResponseDto;
import com.droute.orderservice.dto.response.OrderResponseDto;
import com.droute.orderservice.dto.response.PaymentResponseDto;
import com.droute.orderservice.entity.Order;
import com.droute.orderservice.enums.OrderStatus;
import com.droute.orderservice.feign.client.DriverServiceClient;
import com.droute.orderservice.feign.client.UserServiceClient;
import com.droute.orderservice.repository.OrderRepository;
import com.droute.orderservice.utils.Utils;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

        @Autowired
        private UserServiceClient userServiceClient;

        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private PaymentService paymentService;

        @Autowired
        private DriverServiceClient driverServiceClient;

        @Transactional
        public OrderDetailsResponseDto createOrder(NewOrderRequestDto orderRequest) {
                // Validate the order request

                // Call driver service to get Journey details
                var journeyDetail = driverServiceClient
                                .getJourneyDetailsWithDriverDetailsByJourneyId(orderRequest.getJourneyId()).getData();
                System.out.println(journeyDetail);

                if (orderRequest.getEstimatedFare() == null || orderRequest.getEstimatedFare() == 0.0
                                || orderRequest.getOfferedFare() == null || orderRequest.getOfferedFare() == 0.0) {
                        throw new IllegalArgumentException("Please Enter Valid fare value");
                }

                var order = findOrderByCourierIdAndJourneyId(orderRequest.getCourierId(), orderRequest.getJourneyId());
                if (order != null) {
                        order.setOfferedFare(orderRequest.getOfferedFare());
                        order.setStatus(OrderStatus.PENDING);
                        order = orderRepository.save(order);

                } else {

                        order = Order.builder()
                                        .courierId(orderRequest.getCourierId())
                                        .journeyId(orderRequest.getJourneyId())
                                        .status(OrderStatus.PENDING)
                                        .estimatedFare(orderRequest.getEstimatedFare())
                                        .offeredFare(orderRequest.getOfferedFare())
                                        .userId(orderRequest.getUserId())
                                        .driverId(orderRequest.getDriverId())
                                        .build();
                        order = orderRepository.save(order);
                }

                // Call user service to get Courier details
                var courierDetailRequest = CourierDetailsRequestDto.builder()
                                .status(order.getStatus().toString()).build();
                var courierDetail = userServiceClient
                                .updateCourierDetailsById(order.getCourierId(), courierDetailRequest).getData();

                System.out.println("saved order = " + order);
                return mapToOrderDetailsResponse(order, journeyDetail, courierDetail);
        }

        private Order findOrderByCourierIdAndJourneyId(Long courierId, Long journeyId) {
                return orderRepository.findByCourierIdAndJourneyId(courierId, journeyId);
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
        public OrderDetailsResponseDto updateOrder(Long id, NewOrderRequestDto orderRequest) {
                Order existingOrder = orderRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

                existingOrder.setCourierId(
                                Utils.getUpdatedValue(orderRequest.getCourierId(), existingOrder.getCourierId()));
                existingOrder.setJourneyId(
                                Utils.getUpdatedValue(orderRequest.getJourneyId(), existingOrder.getJourneyId()));
                existingOrder
                                .setStatus(Utils.getUpdatedValue(orderRequest.getOrderStatus(),
                                                existingOrder.getStatus()));
                existingOrder.setEstimatedFare(
                                Utils.getUpdatedValue(orderRequest.getEstimatedFare(),
                                                existingOrder.getEstimatedFare()));
                existingOrder
                                .setOfferedFare(Utils.getUpdatedValue(orderRequest.getOfferedFare(),
                                                existingOrder.getOfferedFare()));

                existingOrder.setUserId(Utils.getUpdatedValue(orderRequest.getUserId(), existingOrder.getUserId()));
                existingOrder.setDriverId(
                                Utils.getUpdatedValue(orderRequest.getDriverId(), existingOrder.getDriverId()));

                existingOrder.setSenderName(
                                Utils.getUpdatedValue(orderRequest.getSenderName(), existingOrder.getSenderName()));
                existingOrder.setSenderContactNo(
                                Utils.getUpdatedValue(orderRequest.getSenderContactNo(),
                                                existingOrder.getSenderContactNo()));
                existingOrder.setSenderLandmarkAddress(Utils.getUpdatedValue(orderRequest.getSenderLandmarkAddress(),
                                existingOrder.getSenderLandmarkAddress()));

                existingOrder.setRecieverName(
                                Utils.getUpdatedValue(orderRequest.getRecieverName(), existingOrder.getRecieverName()));
                existingOrder.setRecieverContactNo(
                                Utils.getUpdatedValue(orderRequest.getRecieverContactNo(),
                                                existingOrder.getRecieverContactNo()));
                existingOrder.setRecieverLandmarkAddress(
                                Utils.getUpdatedValue(orderRequest.getRecieverLandmarkAddress(),
                                                existingOrder.getRecieverLandmarkAddress()));
                existingOrder.setCourierImageUrl1(Utils.getUpdatedValue(orderRequest.getCourierImageUrl1(),
                                existingOrder.getCourierImageUrl1()));
                existingOrder.setCourierImageUrl2(Utils.getUpdatedValue(orderRequest.getCourierImageUrl2(),
                                existingOrder.getCourierImageUrl2()));
                // existingOrder.setPayment(Utils.getUpdatedValue(orderRequest.getPaymentRequestDto(),
                // null));

                Order updatedOrder = orderRepository.save(existingOrder);

                // Call driver service to get Journey details
                var journeyDetail = driverServiceClient
                                .getJourneyDetailsWithDriverDetailsByJourneyId(orderRequest.getJourneyId()).getData();
                System.out.println(journeyDetail);

                // Call user service to get Courier details
                var courierDetailRequest = CourierDetailsRequestDto.builder()
                                .status(updatedOrder.getStatus().toString()).build();
                var courierDetail = userServiceClient
                                .updateCourierDetailsById(updatedOrder.getCourierId(), courierDetailRequest).getData();

                System.out.println(courierDetail);
                return mapToOrderDetailsResponse(updatedOrder, journeyDetail, courierDetail);
        }

        @Transactional
        public void deleteOrder(Long id) {
                Order order = orderRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
                orderRepository.delete(order);
        }

        @Transactional
        public OrderDetailsResponseDto updateOrderStatus(Long id, OrderStatus status) {

                System.out.println("update order status called");
                Order order = orderRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

                order.setStatus(status);
                Order updatedOrder = orderRepository.save(order);

                System.out.println("updated order = " + updatedOrder);

                // Call driver service to get Journey details
                var journeyDetail = driverServiceClient
                                .getJourneyDetailsWithDriverDetailsByJourneyId(updatedOrder.getJourneyId()).getData();
                System.out.println(journeyDetail);

                // Call user service to get Courier details
                var courierDetailRequest = CourierDetailsRequestDto.builder().status(status.toString()).build();
                var courierDetail = userServiceClient
                                .updateCourierDetailsById(updatedOrder.getCourierId(), courierDetailRequest).getData();
                System.out.println(courierDetail);
                return mapToOrderDetailsResponse(updatedOrder, journeyDetail, courierDetail);

        }

        private OrderDetailsResponseDto mapToOrderDetailsResponse(Order order,
                        FilteredJourneyDetailsResponseDto journeyDetail, CourierDetailResponseDto courierDetail) {

                return OrderDetailsResponseDto.builder()
                                .journeyDetails(journeyDetail)
                                .courier(courierDetail)
                                .order(order)
                                .build();
        }

        private OrderResponseDto mapToOrderResponse(Order order) {

                return OrderResponseDto.builder()
                                .id(order.getId())
                                .courierId(order.getCourierId())
                                .journeyId(order.getJourneyId())
                                .status(order.getStatus())
                                .estimatedFare(order.getEstimatedFare())
                                .offeredFare(order.getOfferedFare())
                                .payment(order.getPayment() != null ? PaymentResponseDto.builder()
                                                .id(order.getPayment().getId())
                                                .amount(order.getPayment().getAmount())
                                                .paymentMethod(order.getPayment().getPaymentMethod())
                                                .status(order.getPayment().getStatus())
                                                .transactionId(order.getPayment().getTransactionId())
                                                .build()
                                                : null)
                                .build();

        }

        public AllJourneysOrderResponseDto getOrderByJourneyId(Long id) {

                var orderResponse = new AllJourneysOrderResponseDto();
                orderResponse.setJourneyDetails(driverServiceClient.getJourneyDetailsById(id).getData());
                var orderDetails = orderRepository.findAllByJourneyId(id);
                if (orderDetails.isEmpty()) {
                        throw new EntityNotFoundException("No orders found for journey ID: " + id);
                }
                List<OrderDetailsResponseDto> ordersDetail = new ArrayList<>();
                for (Order order : orderDetails) {
                        if (order.getJourneyId().equals(id)) {
                                var userDetails = userServiceClient.getCourierDetailsById(order.getCourierId());

                                var data = OrderDetailsResponseDto.builder()
                                                .order(order)
                                                .courier(userDetails.getData())
                                                .build();

                                ordersDetail.add(data);

                        }

                }

                orderResponse.setOrderDetails(ordersDetail);
                return orderResponse;
        }

        public List<OrderDetailsResponseDto> getOrderDetailsByUserId(Long userId, String status) {

                List<Order> orders = new ArrayList<>();
                if (status.toUpperCase().equals("ALL")) {
                        orders = orderRepository.findAllByUserId(userId);
                } else {

                        orders = orderRepository.findByUserIdAndStatus(userId,
                                        OrderStatus.valueOf(status.toUpperCase()));
                }

                if (orders == null || orders.isEmpty()) {
                        throw new EntityNotFoundException("No order available for user with id = " + userId);

                }

                List<OrderDetailsResponseDto> data = new ArrayList<>();

                for (Order order : orders) {

                        var journeyDetail = driverServiceClient
                                        .getJourneyDetailsWithDriverDetailsByJourneyId(order.getJourneyId())
                                        .getData();

                        var courierDetail = userServiceClient.getCourierDetailsById(order.getCourierId()).getData();

                        data.add(OrderDetailsResponseDto.builder()
                                        .journeyDetails(journeyDetail)
                                        .order(order)
                                        .courier(courierDetail)
                                        .build());

                }

                return data;

        }

        public List<OrderDetailsResponseDto> getOrderDetailsByJourneyId(Long journeyId, String status) {

                List<Order> orders = new ArrayList<>();

                if (status.toUpperCase().equals("ALL")) {
                        orders = orderRepository.findAllByJourneyId(journeyId);
                } else {
                        orders = orderRepository.findByJourneyIdAndStatus(journeyId,
                                        OrderStatus.valueOf(status.toUpperCase()));
                }

                if (orders == null || orders.isEmpty()) {
                        throw new EntityNotFoundException("No order available for journey with id = " + journeyId);

                }

                List<OrderDetailsResponseDto> data = new ArrayList<>();

                for (Order order : orders) {

                        var journeyDetail = driverServiceClient
                                        .getJourneyDetailsWithDriverDetailsByJourneyId(order.getJourneyId())
                                        .getData();

                        var courierDetail = userServiceClient.getCourierDetailsById(order.getCourierId()).getData();

                        data.add(OrderDetailsResponseDto.builder()
                                        .journeyDetails(journeyDetail)
                                        .order(order)
                                        .courier(courierDetail)
                                        .build());

                }

                return data;

        }
}