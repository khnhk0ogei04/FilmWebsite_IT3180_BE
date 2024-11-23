package com.example.filmxxx.repository;

import com.example.filmxxx.entity.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> findByMovieId(Long movieId);

    @Query("SELECT s FROM ScheduleEntity s JOIN s.movie m WHERE LOWER(m.movieName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<ScheduleEntity> findByMovieNameContainingIgnoreCase(@Param("search") String search, Pageable pageable);

    @Query("SELECT s.id FROM ScheduleEntity s WHERE s.movie.id = :movieId")
    List<Integer> findScheduleIdsByMovieId(@Param("movieId") Long movieId);

    @Query("SELECT s FROM ScheduleEntity s WHERE s.cinema.id = :cinemaId AND s.shift.id = :shiftId AND s.scheduleDate = :scheduleDate")
    List<ScheduleEntity> findConflictSchedules(@Param("cinemaId") Long cinemaId, @Param("shiftId") Long shiftId, @Param("scheduleDate") Date scheduleDate);

}
