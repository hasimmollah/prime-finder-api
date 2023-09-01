package com.nw.primefinder;

import com.nw.primefinder.model.ErrorCodes;
import com.nw.primefinder.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = { "com.nw.primefinder.controller" })
@Slf4j
public class PrimeFinderControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                ErrorCodes.APPLICATION_EXCEPTION.getCode(),
                ErrorCodes.APPLICATION_EXCEPTION.getDescription());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ErrorResponse error = new ErrorResponse(
                ErrorCodes.METHOD_ARGUMENT_MISMATCH_EXCEPTION.getCode(),
                ErrorCodes.METHOD_ARGUMENT_MISMATCH_EXCEPTION.getDescription() + " for  " + ex.getName() + ", value : " + ex.getValue());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST);
    }

    //
}
