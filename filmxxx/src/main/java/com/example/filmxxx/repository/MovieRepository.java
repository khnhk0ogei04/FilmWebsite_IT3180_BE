package com.example.filmxxx.repository;

import com.example.filmxxx.dto.MovieStatisticsDTO;
import com.example.filmxxx.entity.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    Page<MovieEntity> findByStatusAndMovieNameContainingIgnoreCase(Integer status, String name, Pageable pageable);
    List<MovieEntity> findByStatus(Integer status);
    Page<MovieEntity> findByStatus(Integer status, Pageable pageable);

    @Query("""
        SELECT new com.example.filmxxx.dto.MovieStatisticsDTO(
            m.id,
            m.movieName,
            m.image,
            COALESCE(AVG(r.ratingValue), 0.0),
            COUNT(DISTINCT bd.id),
            COALESCE((SELECT SUM(bd2.discountedPrice) FROM BookingDetailsEntity bd2 WHERE bd2.schedule.movie.id = m.id), 0L),
            m.status
        )
        FROM MovieEntity m
        LEFT JOIN RatingEntity r ON m.id = r.movie.id
        LEFT JOIN ScheduleEntity s ON m.id = s.movie.id
        LEFT JOIN BookingDetailsEntity bd ON s.id = bd.schedule.id
        GROUP BY m.id
        ORDER BY
            CASE
                WHEN :sort = 'rating' THEN COALESCE(AVG(r.ratingValue), 0.0)
                WHEN :sort = 'tickets' THEN COUNT(DISTINCT bd.id)
                WHEN :sort = 'revenue' THEN COALESCE(SUM(DISTINCT bd.discountedPrice), 0L)
                ELSE m.id
            END DESC
    """)
    Page<MovieStatisticsDTO> getMovieStatistics(
            @Param("sort") String sort,
            Pageable pageable
    );

    @Query("SELECT AVG(r.ratingValue) FROM RatingEntity r WHERE r.movie.id = :movieId")
    Double calculateAverageRatingByMovieId(@Param("movieId") Long movieId);

}