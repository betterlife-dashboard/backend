package com.betterlife.auth.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class UserResponse {
    private Long id;
    private String email;
    private String name;

    @Builder
    public UserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
