package com.example.community_parc.repository;

import com.example.community_parc.domain.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    Gallery findByGalleryName(String gallery);
}
