package com.example.community_parc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class PostEditDTO {

    @Getter
    @Setter
    public static class Request {
        String title;
        String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        String title;
        String content;
    }
}
