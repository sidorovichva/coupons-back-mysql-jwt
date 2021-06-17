package com.vs.couponsbackmysqljwt.Advice;

import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTExceptionHandler;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(CouponRESTExceptionHandler.class)
    public ResponseEntity<?> handleCustomException(CouponRESTExceptionHandler exception) {
        return new ResponseEntity<>(exception.getText(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<?> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }
}
