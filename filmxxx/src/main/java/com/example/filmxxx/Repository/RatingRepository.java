package com.example.filmxxx.Repository;

import com.example.filmxxx.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    List<RatingEntity> findByMovieId(Long movieId);

    List<RatingEntity> findByUserId(Long userId);

    @Query("SELECT AVG(r.ratingValue) FROM RatingEntity r WHERE r.movie.id = :movieId")
    Double findAverageRatingByMovieId(@Param("movieId") Long movieId);
}
