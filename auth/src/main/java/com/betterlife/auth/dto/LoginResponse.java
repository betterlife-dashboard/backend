package com.betterlife.auth.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString(exclude = "token")
public class LoginResponse {
    private String token;

    @Builder
    public LoginResponse(String token) {
        this.token = token;
    }
}
