package com.example.filmxxx.Repository;

import com.example.filmxxx.dto.SeatDTO;
import com.example.filmxxx.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    @Query("SELECT COUNT(s) FROM SeatEntity s WHERE s.cinema.id = :cinemaId")
    Long countSeatsByCinemaId(@Param("cinemaId") Long cinemaId);

    @Query("SELECT s.cinema.id FROM ScheduleEntity s WHERE s.id = :scheduleId")
    Long findCinemaIdByScheduleId(@Param("scheduleId") Long scheduleId);

    List<SeatEntity> findByCinemaId(Long cinemaId);
}
