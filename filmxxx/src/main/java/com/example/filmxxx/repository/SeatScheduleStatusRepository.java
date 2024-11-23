package com.example.filmxxx.repository;

import com.example.filmxxx.entity.SeatScheduleStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatScheduleStatusRepository extends JpaRepository<SeatScheduleStatusEntity, Long> {
    List<SeatScheduleStatusEntity> findByScheduleId(Long scheduleId);

    // Find booked seats
    @Query("SELECT s.seat.id FROM SeatScheduleStatusEntity s WHERE s.schedule.id = :scheduleId")
    List<Integer> findBookedSeatIdsByScheduleId(@Param("scheduleId") Long scheduleId);

}
