package com.example.community_parc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GalleryController {

    @GetMapping("/{gallery}")
    public String gallery(@PathVariable String gallery) { //페이징 필요 나중에

        return gallery;
    }
}
