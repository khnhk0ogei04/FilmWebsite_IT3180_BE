package com.example.filmxxx.exception;

public class MovieException{
    public static class MovieNotFoundException extends RuntimeException{
        public MovieNotFoundException(Long movieId){
            super("Movie with id " + movieId + " not found");
        }
    }
}
