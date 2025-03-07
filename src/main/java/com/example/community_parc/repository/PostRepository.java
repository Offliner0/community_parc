package com.example.community_parc.repository;

import com.example.community_parc.domain.Gallery;
import com.example.community_parc.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByGallery(Gallery gallery);

    Page<Post> findByGallery(Gallery gallery, Pageable pageable);
}
