package com.example.community_parc.service;

import com.example.community_parc.domain.Gallery;
import com.example.community_parc.repository.GalleryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryService {

    private final GalleryRepository galleryRepository;

    public GalleryService(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    public void addGallery(String galleryname) {
        Gallery galleryObj = new Gallery(galleryname);

        galleryRepository.save(galleryObj);
    }

    public List<Gallery> getGalleryList(){
        return galleryRepository.findAll();
    }
}
