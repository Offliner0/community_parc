package com.example.community_parc.repository;

import com.example.community_parc.domain.Comment;
import com.example.community_parc.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
