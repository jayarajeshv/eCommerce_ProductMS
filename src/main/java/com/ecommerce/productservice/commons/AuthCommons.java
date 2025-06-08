package com.ecommerce.productservice.commons;

import com.ecommerce.productservice.dtos.UserResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthCommons {

    private final RestTemplate restTemplate;

    public AuthCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserResponseDto validateToken(String tokenValue) {
        return restTemplate.getForObject("http://localhost:9080/auth/validate/" + tokenValue, UserResponseDto.class);
    }
}
