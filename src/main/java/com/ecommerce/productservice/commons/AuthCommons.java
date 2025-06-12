package com.ecommerce.productservice.commons;

import com.ecommerce.productservice.dtos.UserResponseDto;
import com.ecommerce.productservice.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthCommons {

    private final RestTemplate restTemplate;

    public AuthCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserResponseDto validateToken(String tokenValue) throws InvalidTokenException {
        try {
            return restTemplate.getForObject("http://localhost:9080/auth/validate/" + tokenValue, UserResponseDto.class);
        } catch (RestClientException e) {
            throw new InvalidTokenException("Invalid token");
        }
    }
}