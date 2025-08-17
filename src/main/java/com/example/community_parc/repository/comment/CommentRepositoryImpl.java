package com.example.community_parc.repository.comment;

import com.example.community_parc.domain.QComment;
import com.example.community_parc.domain.QPost;
import com.example.community_parc.dto.CommentPainationDto;
import com.example.community_parc.dto.PostPaginationDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentPainationDto> commentPainations(UUID postId, int pageNo, int pageSize){
        QComment comment = QComment.comment;

        List<UUID> ids = queryFactory.
                select(comment.id)
                .from(comment)
                .where(comment.post.id.eq(postId))
                .orderBy(comment.createdAt.desc())//맞음?
                .limit(pageSize)
                .offset(pageNo * pageSize)
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.fields(CommentPainationDto.class,
                        comment.content,
                        comment.author,
                        comment.clientIP,
                        comment.replyYN,
                        comment.replyNUM,
                        comment.createdAt,
                        comment.updatedAt))
                .from(comment)
                .where(comment.post.id.in(ids))
                .orderBy(comment.createdAt.desc())
                .fetch();

    }
}
