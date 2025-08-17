package com.example.community_parc.repository;

import com.example.community_parc.domain.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GalleryRepository extends JpaRepository<Gallery, UUID> {

    Gallery findByGalleryName(String gallery);
}
