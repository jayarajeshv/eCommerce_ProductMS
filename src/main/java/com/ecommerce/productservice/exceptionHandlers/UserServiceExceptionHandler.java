package com.ecommerce.productservice.exceptionHandlers;

import com.ecommerce.productservice.dtos.ExceptionResponseDto;
import com.ecommerce.productservice.exceptions.UnAuthorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserServiceExceptionHandler {
    @ExceptionHandler(UnAuthorizedAccessException.class)
    public ResponseEntity<ExceptionResponseDto> handleUnAuthorizedAccessException(UnAuthorizedAccessException exception) {
        ExceptionResponseDto dto = new ExceptionResponseDto();
        dto.setMessage(exception.getMessage());
        dto.setResolution("Please try with correct credentials");
        return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
    }
}
