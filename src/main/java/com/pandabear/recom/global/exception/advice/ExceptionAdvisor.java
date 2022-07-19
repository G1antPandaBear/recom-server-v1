package com.pandabear.recom.global.exception.advice;

import com.pandabear.recom.global.exception.BusinessException;
import com.pandabear.recom.global.exception.ro.ExceptionRO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionRO> definedException(Exception e) {
        final ExceptionRO exceptionRO = new ExceptionRO(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionRO);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ExceptionRO> businessLogicException(
            BusinessException e) {
        final ExceptionRO exceptionRO = new ExceptionRO(e.getMessage());
        return ResponseEntity.status(e.getStatus())
                .body(exceptionRO);
    }
}
