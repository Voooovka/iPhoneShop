package com.javovka.userservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class ModelFoundException extends RuntimeException{

    public ModelFoundException(String message) {
        super(message);
    }
}
