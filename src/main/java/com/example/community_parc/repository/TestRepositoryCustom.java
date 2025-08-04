package com.example.community_parc.repository;

import com.example.community_parc.domain.TestEntity;

import java.util.List;

public interface TestRepositoryCustom {
    List<TestEntity> findByName(String name);
}
