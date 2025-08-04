package com.example.community_parc.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DummyDataInitializer implements CommandLineRunner {

    private final PostInsert postInsert;
    @Override
    public void run(String... args) {
        int count = 10000000; // 원하는 개수
//        postInsert.insertPost(count);
    }
}

