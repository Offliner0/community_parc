package com.example.community_parc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestPostEditRequestDTO {
    String title;
    String content;
    String password;
}
