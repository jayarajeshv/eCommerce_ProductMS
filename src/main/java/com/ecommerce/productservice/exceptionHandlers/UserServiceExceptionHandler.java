package com.ecommerce.productservice.exceptionHandlers;

import com.ecommerce.productservice.dtos.ExceptionResponseDto;
import com.ecommerce.productservice.exceptions.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserServiceExceptionHandler {
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionResponseDto> handleInvalidTokenException(InvalidTokenException exception) {
        ExceptionResponseDto dto = new ExceptionResponseDto();
        dto.setMessage(exception.getMessage());
        dto.setResolution("Token validation failed. Please ensure the token is correct and not expired.");
        return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
    }
}
