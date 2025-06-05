package com.droute.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.droute.orderservice.dto.request.OrderRequestDto;
import com.droute.orderservice.dto.response.AllJourneysOrderResponseDto;
import com.droute.orderservice.dto.response.CommonResponseDto;
import com.droute.orderservice.dto.response.OrderResponseDto;
import com.droute.orderservice.dto.response.ResponseBuilder;
import com.droute.orderservice.enums.OrderStatus;
import com.droute.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto response = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseDto<OrderResponseDto>> getOrderById(@PathVariable Long id) {
        OrderResponseDto response = orderService.getOrderById(id);
        return ResponseBuilder.success(HttpStatus.OK, "Order details fetched successfully", response);
    }

    @GetMapping("/journey/{journeyId}")
    public ResponseEntity<CommonResponseDto<AllJourneysOrderResponseDto>> getOrderDetailsJourneyId(@PathVariable Long journeyId) {
        var response = orderService.getOrderByJourneyId(journeyId);
        return ResponseBuilder.success(HttpStatus.OK, "Order details fetched successfully", response);
    }



    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> responses = orderService.getAllOrders();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable Long id, 
            @RequestBody OrderRequestDto orderRequestDtoOrderRequestDto) {
        OrderResponseDto response = orderService.updateOrder(id, orderRequestDtoOrderRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        OrderResponseDto response = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(response);
    }
}