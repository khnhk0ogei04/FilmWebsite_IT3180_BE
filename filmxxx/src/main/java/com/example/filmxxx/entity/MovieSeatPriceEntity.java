package com.example.filmxxx.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_seat_prices")
public class MovieSeatPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    @Column(name = "seat_type", nullable = false)
    private String seatType;

    @Column(name = "base_price", nullable = false)
    private Long basePrice;

}
