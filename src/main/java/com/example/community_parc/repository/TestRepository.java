package com.example.community_parc.repository;

import com.example.community_parc.domain.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long>, TestRepositoryCustom {
}
