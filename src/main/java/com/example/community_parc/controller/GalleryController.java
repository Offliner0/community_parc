package com.example.community_parc.controller;

import com.example.community_parc.domain.Gallery;
import com.example.community_parc.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    //갤러리 리스트
    @GetMapping("/{gallery}/list")
    public List<Gallery> gallery(@PathVariable String gallery) { //페이징 필요 나중에

        return galleryService.getGalleryList();
    }

    //테스트용 갤러리 추가
    @GetMapping("/addgallery/{gallery}")
    public HttpStatus addGallery(@PathVariable String gallery) {

        galleryService.addGallery(gallery);

        return HttpStatus.OK;

    }
}
