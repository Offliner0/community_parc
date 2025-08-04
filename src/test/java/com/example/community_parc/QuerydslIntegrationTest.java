package com.example.community_parc;

import com.example.community_parc.domain.TestEntity;
import com.example.community_parc.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QuerydslIntegrationTest {
    @Autowired
    private TestRepository testRepository;

    @Test
    void querydsl적용확인() {
        // given
        TestEntity t1 = new TestEntity("alpha");
        TestEntity t2 = new TestEntity("beta");
        testRepository.save(t1);
        testRepository.save(t2);

        // when
        List<TestEntity> result = testRepository.findByName("alpha");

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("alpha");
    }
}
