package com.example.webdevelopment.services;

import lombok.Value;

@Value
public class ErrorMessage {

    private String message;

    public static ErrorMessage from(final String message) {
        return new ErrorMessage(message);
    }
}
