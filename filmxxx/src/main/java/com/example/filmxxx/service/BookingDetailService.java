package com.example.filmxxx.service;

import com.example.filmxxx.repository.BookingDetailRepository;
import com.example.filmxxx.repository.SeatScheduleStatusRepository;
import com.example.filmxxx.repository.UserRepository;
import com.example.filmxxx.dto.BookingDetailsDTO;
import com.example.filmxxx.entity.BookingDetailsEntity;
import com.example.filmxxx.entity.SeatScheduleStatusEntity;
import com.example.filmxxx.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BookingDetailService {

        @Autowired
        private BookingDetailRepository bookingDetailsRepository;

        @Autowired
        private SeatScheduleStatusRepository seatScheduleStatusRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ModelMapper modelMapper;

        public Page<BookingDetailsDTO> getBookingsByUserId(Long userId, Integer page, Integer size) {
            Pageable pageable = PageRequest.of(page, size);
            Page<BookingDetailsEntity> bookingEntities = bookingDetailsRepository.findByUserId(userId, pageable);
            return bookingEntities.map(booking -> modelMapper.map(booking, BookingDetailsDTO.class));
        }

        @Transactional
        public BookingDetailsDTO saveBookingDetails(BookingDetailsDTO bookingDetailsDTO) {

            UserEntity user = userRepository.findById(bookingDetailsDTO.getUserId()).orElse(null);

            if (user.getAccountBalance() < bookingDetailsDTO.getDiscountedPrice()) {
                throw new RuntimeException("Not enough money");
            }

            Long updateBalance = user.getAccountBalance() - bookingDetailsDTO.getDiscountedPrice();
            user.setAccountBalance(updateBalance);
            userRepository.save(user);

            BookingDetailsEntity bookingDetails = modelMapper.map(bookingDetailsDTO, BookingDetailsEntity.class);
            bookingDetails.setPaymentStatus(true);
            bookingDetails.setOrderTime(LocalDateTime.now());
            BookingDetailsEntity savedBooking = bookingDetailsRepository.save(bookingDetails);

            SeatScheduleStatusEntity seatStatus = new SeatScheduleStatusEntity();
            seatStatus.setSeat(savedBooking.getSeat());
            seatStatus.setSchedule(savedBooking.getSchedule());
            seatStatus.setStatus(1);
            seatScheduleStatusRepository.save(seatStatus);
            return modelMapper.map(savedBooking, BookingDetailsDTO.class);
        }
    }

