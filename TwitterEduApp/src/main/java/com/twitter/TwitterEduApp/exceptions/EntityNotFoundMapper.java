package com.twitter.TwitterEduApp.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

//dzieki @ControllerAdvice adnotacji kontroler może obsługiwać wyjątki
@ControllerAdvice
public class EntityNotFoundMapper {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Zasób nie istnieje")
    public void handleNotFound() {
    }
}
