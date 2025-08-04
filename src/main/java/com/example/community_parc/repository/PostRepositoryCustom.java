package com.example.community_parc.repository;

import com.example.community_parc.domain.Gallery;
import com.example.community_parc.domain.Post;
import com.example.community_parc.dto.PostDTO;
import com.example.community_parc.dto.PostPaginationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PostRepositoryCustom {
    public List<PostPaginationDto> customFindAll(UUID galleryId, int pageNo, int pageSize);
}
