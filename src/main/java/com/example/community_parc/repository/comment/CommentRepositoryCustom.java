package com.example.community_parc.repository.comment;

import com.example.community_parc.dto.CommentPainationDto;

import java.util.List;
import java.util.UUID;

public interface CommentRepositoryCustom {
    public List<CommentPainationDto> commentPainations(UUID postId, int pageNo, int pageSize);

}
