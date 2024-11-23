package com.example.filmxxx.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailsDTO {
    private Long id;
    private Long userId;
    private Long seatId;
    private Long scheduleId;
    private Long originalPrice;
    private Long discountedPrice;
    private LocalDateTime orderTime;
    private Boolean paymentStatus;
}
