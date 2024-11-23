package com.example.filmxxx.Repository;

import com.example.filmxxx.entity.BookingDetailsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetailsEntity, Long> {
    @Query("SELECT COUNT(DISTINCT bd.seat.id) FROM BookingDetailsEntity bd WHERE bd.schedule.movie.id = :movieId ")
    Long countTicketsSoldByMovieId(@Param("movieId") Long movieId);

    // Đếm số ghế đã đặt cho một lịch chiếu
    Long countByScheduleId(Long scheduleId);

    // Đếm tổng số vé đã bán cho một danh sách các lịch chiếu
    @Query("SELECT COUNT(bd) FROM BookingDetailsEntity bd WHERE bd.schedule.id IN :scheduleIds")
    Long countByScheduleIds(@Param("scheduleIds") List<Integer> scheduleIds);

    Boolean existsBySeatIdAndScheduleId(Long seatId, Long scheduleId);

    @Query("SELECT SUM(bd.discountedPrice) FROM BookingDetailsEntity bd WHERE bd.schedule.movie.id = :movieId")
    Long calculateRevenueByMovieId(@Param("movieId") Long movieId);

    Page<BookingDetailsEntity> findByUserId(Long userId, Pageable pageable);
}
