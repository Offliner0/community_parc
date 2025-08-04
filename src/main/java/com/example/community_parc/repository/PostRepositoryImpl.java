package com.example.community_parc.repository;

import com.example.community_parc.domain.Gallery;
import com.example.community_parc.domain.Post;
import com.example.community_parc.domain.QPost;
import com.example.community_parc.dto.PostDTO;
import com.example.community_parc.dto.PostPaginationDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    //흐름: 커버링 인덱스로 id를 가져온다. -> id를 기반으로 페이징 요소를 가져온다.
    @Override
    public List<PostPaginationDto> customFindAll(UUID galleryId, int pageNo, int pageSize) {
        QPost post = QPost.post;

        List<UUID> ids = queryFactory //pk가 시간순으로 정렬 가능해야 할거같음
                .select(post.id)
                .from(post)
                .where(post.gallery.id.eq(galleryId))
                .orderBy(post.createdAt.desc())//맞음?
                .limit(pageSize)
                .offset(pageNo * pageSize)
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.fields(PostPaginationDto.class,
                        post.id,
                        post.title,
                        post.author,
                        post.clientIp,
                        post.createdAt,
                        post.updatedAt,
                        post.likes))
                .from(post)
                .where(post.id.in(ids))
                .orderBy(post.createdAt.desc())
                .fetch();
                //조인해서 닉네임도 가져오려면 어떻게 해야될까?
                //memberid가져와서 닉네임따면될듯..?
                // 근데 비회원이랑 회원이랑 같이 있는데 어케함?
    }
}
