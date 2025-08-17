package com.example.community_parc.repository.post;

import com.example.community_parc.domain.Gallery;
import com.example.community_parc.domain.Member;
import com.example.community_parc.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID>, PostRepositoryCustom {

    List<Post> findByGallery(Gallery gallery);

    Page<Post> findByGallery(Gallery gallery, Pageable pageable);

    Page<Post> findByMember(Member member, Pageable pageable);

}
