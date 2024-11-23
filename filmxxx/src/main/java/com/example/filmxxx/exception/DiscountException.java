package com.example.filmxxx.exception;

public class DiscountException {
    public static class DiscountMovieExistedException extends RuntimeException {
        public DiscountMovieExistedException(Long movieId) {
            super("Discount movie existed: " + movieId);
        }
    }
}
