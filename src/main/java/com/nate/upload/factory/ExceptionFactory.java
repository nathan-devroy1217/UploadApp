package com.nate.upload.factory;

import com.nate.upload.exception.CustomFileException;

public class ExceptionFactory {

    public static CustomFileException createCustomFileException(String message) {
        return new CustomFileException(message);
    }
}
