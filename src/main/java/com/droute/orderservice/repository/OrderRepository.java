package com.droute.orderservice.repository;

import com.droute.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByJourneyId(Long journeyId);
    // Custom query methods can be added here
    // Example:
    // List<Order> findByOrderStatus(OrderStatus status);
}