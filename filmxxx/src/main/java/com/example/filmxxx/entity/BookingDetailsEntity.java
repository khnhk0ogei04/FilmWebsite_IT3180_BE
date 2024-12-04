package com.example.filmxxx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "booking_details")
public class BookingDetailsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private SeatEntity seat;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private ScheduleEntity schedule;

    @Column(name = "original_price", nullable = false)
    private Long originalPrice;

    @Column(name = "discounted_price")
    private Long discountedPrice;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @Column(name = "payment_status", nullable = false)
    private Boolean paymentStatus;


}
