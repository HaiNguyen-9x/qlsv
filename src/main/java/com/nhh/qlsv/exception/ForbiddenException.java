package com.nhh.qlsv.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("You do not have authority.");
    }
}
