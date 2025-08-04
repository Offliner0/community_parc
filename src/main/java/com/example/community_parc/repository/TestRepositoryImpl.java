package com.example.community_parc.repository;

import com.example.community_parc.domain.QTestEntity;
import com.example.community_parc.domain.TestEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TestRepositoryImpl implements TestRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<TestEntity> findByName(String name) {
        QTestEntity test = QTestEntity.testEntity;
        return queryFactory
                .selectFrom(test)
                .where(test.name.eq(name))
                .fetch();
    }
}
