package com.example.filmxxx.controllers;

import com.example.filmxxx.dto.BookingDetailsDTO;
import com.example.filmxxx.service.BookingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingDetailsController {

    @Autowired
    private BookingDetailService bookingDetailService;

    @GetMapping
    public ResponseEntity<Page<BookingDetailsDTO>> getBookingsByUserId(
            @RequestParam(name = "userId") Long userId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ){
        Page<BookingDetailsDTO> bookingsList = bookingDetailService.getBookingsByUserId(userId, page, size);
        return ResponseEntity.ok(bookingsList);
    }

    @PostMapping
    public ResponseEntity<BookingDetailsDTO> createBooking(@RequestBody BookingDetailsDTO bookingDetailsDTO) {
        BookingDetailsDTO savedBooking = bookingDetailService.saveBookingDetails(bookingDetailsDTO);
        return ResponseEntity.ok(savedBooking);
    }
}
