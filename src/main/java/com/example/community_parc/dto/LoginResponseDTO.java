package com.example.community_parc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }
}
