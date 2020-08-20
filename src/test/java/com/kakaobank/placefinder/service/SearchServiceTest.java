package com.kakaobank.placefinder.service;

import com.kakaobank.placefinder.entity.KeywordCount;
import com.kakaobank.placefinder.model.PlaceResponseData;
import com.kakaobank.placefinder.repository.RankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchServiceTest {
    @Autowired
    private SearchService searchService;
    @Autowired
    private RankRepository rankRepository;

    private final int PAGE_NO = 1;
    private final String SEARCH_KEYWORD = "카카오뱅크";
    @Test
    void call() throws Exception {
        PlaceResponseData placeResponseData = searchService.call(SEARCH_KEYWORD, PAGE_NO);
        assertEquals(placeResponseData.getPlaces().size() > 0, true);
    }

    private final int LOOP_COUNT = 89;
    private final String LOOP_KEYWORD = "판교";
    @Test
    void updateKeywordCount() {
        for(int i=0; i<LOOP_COUNT; i++) {
            searchService.updateKeywordCount(LOOP_KEYWORD);
        }
        Optional<KeywordCount> keyword = rankRepository.findByKeyword(LOOP_KEYWORD);
        assertEquals(keyword.get().getCount(), LOOP_COUNT);
    }
}