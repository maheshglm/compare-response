package com.gojek.program.exceptions;

import org.slf4j.helpers.MessageFormatter;

public class Exception extends RuntimeException {
    private final ExceptionType exceptionType;

    public Exception(ExceptionType exceptionType, String message, Object... args) {
        super(MessageFormatter.arrayFormat(message,args).getMessage());
        this.exceptionType = exceptionType;
    }
}
