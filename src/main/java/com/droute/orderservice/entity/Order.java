package com.droute.orderservice.entity;

import com.droute.orderservice.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long courierId;
    private Long journeyId;

    private Double estimatedFare;
    private Double offeredFare;
    private Long userId; 
    private Long driverId; 

    //Below details taken when order is placed
    private String senderName;
    private String senderContactNo;
    private String senderLandmarkAddress;
    private String recieverName;
    private String recieverContactNo;
    private String recieverLandmarkAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String courierImageUrl1;
    private String courierImageUrl2;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private Payment payment;
}