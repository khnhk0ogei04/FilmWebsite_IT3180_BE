package com.example.filmxxx.exception;

public class CinemaException {
    public static class CinemaNotFoundException extends RuntimeException {
        public CinemaNotFoundException(Long cinemaId) {
            super("Cinema with id " + cinemaId + " not found");
        }
    }
}
