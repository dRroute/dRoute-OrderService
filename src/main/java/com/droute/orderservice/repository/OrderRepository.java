package com.droute.orderservice.repository;

import com.droute.orderservice.entity.Order;
import com.droute.orderservice.enums.OrderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByJourneyId(Long journeyId);
    // Custom query methods can be added here
    // Example:
    // List<Order> findByOrderStatus(OrderStatus status);

    Order findByCourierIdAndJourneyId(Long courierId, Long journeyId);

    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);

    List<Order> findByJourneyIdAndStatus(Long journeyId, OrderStatus status);

    List<Order> findAllByUserId(Long userId);
}