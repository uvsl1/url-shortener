package com.uvsl.urlShortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestErrorMessage {

    public static final String EXPIRATION_LIMIT_EXCEEDED = "Expiration time cannot be greater than 90 days.";
}