package com.example.community_parc.repository.post;

import com.example.community_parc.dto.PostPaginationDto;

import java.util.List;
import java.util.UUID;

public interface PostRepositoryCustom {
    public List<PostPaginationDto> customFindAll(UUID galleryId, int pageNo, int pageSize);
}
