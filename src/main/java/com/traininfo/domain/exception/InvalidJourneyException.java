package com.traininfo.domain.exception;

public class InvalidJourneyException extends IllegalArgumentException {

    public InvalidJourneyException(String message) {
        super(message);
    }
}
