package com.example.filmxxx.exception;

public class ScheduleException {
    public static class ScheduleNotFoundException extends RuntimeException {
        public ScheduleNotFoundException(Long scheduleId) {
            super("Role with id " + scheduleId + " not found");
        }
    }
}
