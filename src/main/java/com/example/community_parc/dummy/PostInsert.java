package com.example.community_parc.dummy;

import com.example.community_parc.domain.AuthorType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostInsert {
    private final JdbcTemplate jdbcTemplate;
    private static final int BATCH_SIZE = 10000;

    public void insertPost(int totalCount) {
        String sql = "INSERT INTO post (id, title, content, deleteYN, views, author_type, client_ip, created_at, updated_at, password, likes, author, gallery_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Object[]> batch = new ArrayList<>();

        for (int i = 1; i <= totalCount; i++) {
            batch.add(new Object[]{
                    UUID.randomUUID(),
                    "test", // store_id
                    "content " + i,
                    0,//deleteYN
                    i,
                    1,//authorType
                    "00",//IP
                    Timestamp.valueOf(LocalDateTime.now()),
                    Timestamp.valueOf(LocalDateTime.now()),
                    "0000",//password
                    i,//like
                    "test",//author
                    UUID.fromString("c0fb0bf0-2b0f-4ef3-9b2b-5567f7fa2f49")//gallery fk
            });

            if (i % BATCH_SIZE == 0 || i == totalCount) {
                jdbcTemplate.batchUpdate(sql, batch);
                batch.clear();
            }
        }

        System.out.println("✅ 데이터 삽입 완료: " + totalCount + "건");
    }
}
