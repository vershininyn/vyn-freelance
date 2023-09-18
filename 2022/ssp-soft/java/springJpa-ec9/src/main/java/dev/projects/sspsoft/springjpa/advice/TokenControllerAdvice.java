package dev.projects.sspsoft.springjpa.advice;

import io.jsonwebtoken.ExpiredJwtException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<ErrorMessage> handleServletException(ExpiredJwtException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(ex.getLocalizedMessage()));
    }
}
