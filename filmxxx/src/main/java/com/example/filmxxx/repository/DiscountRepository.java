package com.example.filmxxx.repository;

import com.example.filmxxx.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {

    DiscountEntity findByMovieId(Long movieId);

    DiscountEntity findByDiscountPercentageBetween(Long min, Long max);
}
