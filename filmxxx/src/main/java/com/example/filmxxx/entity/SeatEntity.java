package com.example.filmxxx.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "seats")
public class SeatEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_type", nullable = false, length = 50)
    private String seatType;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private CinemaEntity cinema;

    @Column(name = "seat_row", nullable = false, length = 2)
    private String seatRow;

    @Column(name = "seat_number", nullable = false)
    private Long seatNumber;
}
