package com.nate.upload.exception;

import com.nate.upload.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class FileTransferExceptionHandler {

    @ExceptionHandler({ CustomFileException.class })
    public ResponseEntity<ApiError> handleFantasyResourceNotFoundException(
            CustomFileException ex, WebRequest request) {

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .error(ex.getMessage())
                .build();

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
