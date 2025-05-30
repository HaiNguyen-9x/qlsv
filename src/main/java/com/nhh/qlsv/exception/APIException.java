package com.nhh.qlsv.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException {
    @Getter
    HttpStatus status;
    String message;

    public APIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public APIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
