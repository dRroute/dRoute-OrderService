package com.droute.orderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.droute.orderservice.dto.request.PaymentRequestDto;
import com.droute.orderservice.dto.response.PaymentResponseDto;
import com.droute.orderservice.entity.Order;
import com.droute.orderservice.entity.Payment;
import com.droute.orderservice.enums.PaymentStatus;
import com.droute.orderservice.repository.OrderRepository;
import com.droute.orderservice.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;


    @Transactional
    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + paymentRequest.getOrderId()));

        Payment payment = Payment.builder()
                .amount(paymentRequest.getAmount())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .transactionId(paymentRequest.getTransactionId())
                .order(order)
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        
        // Set the payment in the order entity
        order.setPayment(savedPayment);
        orderRepository.save(order);

        return mapToPaymentResponse(savedPayment);
    }

    
    public PaymentResponseDto getPaymentByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        
        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new RuntimeException("Payment not found for order id: " + orderId));
        
        return mapToPaymentResponse(payment);
    }


    @Transactional
    public PaymentResponseDto updatePaymentStatus(String paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
        
        payment.setStatus(status);
        Payment updatedPayment = paymentRepository.save(payment);
        return mapToPaymentResponse(updatedPayment);
    }

    private PaymentResponseDto mapToPaymentResponse(Payment payment) {
        return PaymentResponseDto.builder()
                .id(payment.getId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .build();
    }
}