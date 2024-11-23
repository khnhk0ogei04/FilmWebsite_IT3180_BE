package com.example.filmxxx.Repository;

import com.example.filmxxx.entity.MovieSeatPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieSeatPriceRepository extends JpaRepository<MovieSeatPriceEntity, Long> {
    List<MovieSeatPriceEntity> findByMovieId(Long movieId);

    Optional<MovieSeatPriceEntity> findByMovieIdAndSeatType(Long movieId, String seatType);

    @Query("SELECT m.basePrice FROM MovieSeatPriceEntity m WHERE m.movie.id = :movieId AND m.seatType = :seatType")
    Long findPriceByMovieIdAndSeatType(@Param("movieId") Long movieId, @Param("seatType") String seatType);
}
