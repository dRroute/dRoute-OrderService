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

import com.droute.orderservice.dto.request.NewOrderRequestDto;
import com.droute.orderservice.dto.response.AllJourneysOrderResponseDto;
import com.droute.orderservice.dto.response.CommonResponseDto;
import com.droute.orderservice.dto.response.OrderDetailsResponseDto;
import com.droute.orderservice.dto.response.OrderResponseDto;
import com.droute.orderservice.dto.response.ResponseBuilder;
import com.droute.orderservice.enums.OrderStatus;
import com.droute.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<CommonResponseDto<OrderDetailsResponseDto>> createOrder(@RequestBody NewOrderRequestDto orderRequestDto) {
        System.out.println(orderRequestDto);
        var response = orderService.createOrder(orderRequestDto);
        return ResponseBuilder.success(HttpStatus.CREATED, "Order created successfully", response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseDto<OrderResponseDto>> getOrderById(@PathVariable Long id) {
        OrderResponseDto response = orderService.getOrderById(id);
        return ResponseBuilder.success(HttpStatus.OK, "Order details fetched successfully", response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> responses = orderService.getAllOrders();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<CommonResponseDto<OrderDetailsResponseDto>> updateOrder(
            @PathVariable Long orderId, 
            @RequestBody(required = false) NewOrderRequestDto orderRequestDto, @RequestParam(required = false) String status) {

        var response = new OrderDetailsResponseDto ();
        if(orderRequestDto == null && status != null && status != ""){
            response = orderService.updateOrderStatus(orderId, OrderStatus.valueOf(status.toUpperCase()));
        }else{

            response = orderService.updateOrder(orderId, orderRequestDto);
        }

        System.out.println("response = " + response);
        return ResponseBuilder.success(HttpStatus.OK, "Order details updated", response);
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<CommonResponseDto<OrderDetailsResponseDto>> updateOrderByPost(
            @PathVariable Long orderId, 
            @RequestBody(required = false) NewOrderRequestDto orderRequestDto, @RequestParam(required = false) String status) {

        var response = new OrderDetailsResponseDto ();
        if(orderRequestDto == null && status != null && status != ""){
            response = orderService.updateOrderStatus(orderId, OrderStatus.valueOf(status.toUpperCase()));
        }else{

            response = orderService.updateOrder(orderId, orderRequestDto);
        }

        System.out.println("response = " + response);
        return ResponseBuilder.success(HttpStatus.OK, "Order details updated", response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponseDto<List<OrderDetailsResponseDto>>> getOrderDetailsByUserId(
            @PathVariable Long userId,
            @RequestParam String status) {
        List<OrderDetailsResponseDto> response = orderService.getOrderDetailsByUserId(userId, status);
        
        return ResponseBuilder.success(HttpStatus.OK, "Order details found", response);
    }

    // this API is consumed by Driver to see the order details
    @GetMapping("/journey/{journeyId}")
    public ResponseEntity<CommonResponseDto<List<OrderDetailsResponseDto>>>   getOrderDetailsByJourneyId(
            @PathVariable Long journeyId,
            @RequestParam String status) {
        System.out.println("journey order called with status = "+ status + " id = "+7);
        var response = orderService.getOrderDetailsByJourneyId(journeyId, status);
        return ResponseBuilder.success(HttpStatus.OK, "Order details found", response);
    }
}