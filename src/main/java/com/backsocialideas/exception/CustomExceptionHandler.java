package com.backsocialideas.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    private static final String BAD_REQUEST = "BAD_REQUEST";
    private static final String NOT_FOUND = "NOT_FOUND";
    private static final String INVALID_LOGIN = "INVALID_LOGIN";
    private static final String UNPROCESSABLE_ENTITY = "UNPROCESSABLE_ENTITY";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        log.error(" Error on ", ex);
        ErrorResponse error = new ErrorResponse(INTERNAL_SERVER_ERROR, Collections.singletonList(ex.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(BAD_REQUEST,
                        ex.getBindingResult().getFieldErrors().stream()
                                .map(field -> String.format("%s - %s", field.getField(), field.getDefaultMessage())).collect(Collectors.toList())),
                status);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> message = new ArrayList<>();
        message.add(ex.getMessage());
        ErrorResponse error = new ErrorResponse(BAD_REQUEST, message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(NOT_FOUND, Collections.singletonList(ex.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
        List<String> message = new ArrayList<>();
        message.add(ex.getMessage());
        ErrorResponse error = new ErrorResponse(BAD_REQUEST, message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLoginException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidLoginException(InvalidLoginException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(INVALID_LOGIN, Collections.singletonList(ex.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
