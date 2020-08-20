package com.kakaobank.placefinder.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RankServiceTest {
    @Autowired
    private RankService rankService;

    @Test
    void getPopularKeyword() {
        assertEquals(rankService.getPopularKeyword().getRanking().size(), 0);
    }
}