package com.imdb.titles.rest;


import com.imdb.titles.model.ErrorResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TitleExceptionHandler {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TitleExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleError(Exception ex) {
        LOGGER.error(ErrorResponse.GENERAL_ERROR_TEXT, ex);

        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
