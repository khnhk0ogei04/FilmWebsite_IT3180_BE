package com.example.filmxxx.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "discount")
public class DiscountEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount_percentage", nullable = false)
    private Long discountPercentage;

    @Column(name = "notification_title")
    private String notificationTitle;

    @Column(name = "notification_content")
    private String notificationContent;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

}
