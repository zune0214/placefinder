package com.kakaobank.placefinder.controller;

import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.exception.PlaceFinderException;
import com.kakaobank.placefinder.model.PlaceResponseData;
import com.kakaobank.placefinder.model.RankResponseData;
import com.kakaobank.placefinder.model.ServerResponse;
import com.kakaobank.placefinder.service.RankService;
import com.kakaobank.placefinder.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiControllerTest {
    @Autowired
    private SearchService commonCaller;
    @Autowired
    private RankService rankService;

    private ApiController apiController;

    @BeforeEach
    void setUp() {
        apiController = new ApiController(commonCaller, rankService);
    }

    @Test
    void call() throws Exception {
        ServerResponse response = apiController.call("이마트", 1);
        assertEquals(response.isResult(), true);
    }

    @Test
    void call_fail() throws Exception {
        try{
            apiController.call("이마트", 0);
        }catch(PlaceFinderException e){
            assertEquals(e.getResultCode(), StatusCode.E1401.getCode());
        }
    }

    @Test
    void call_fail_empty_keyword() throws Exception {
        try{
            apiController.call("", 1);
        }catch(PlaceFinderException e){
            assertEquals(e.getResultCode(), StatusCode.E1402.getCode());
        }
    }

    @Test
    void keywordRank() throws Exception {
        apiController.call("이마트", 1);
        apiController.call("이마트", 2);
        ServerResponse response = apiController.keywordRank();
        assertEquals(response.getData().getClass(), RankResponseData.class);
        RankResponseData data = (RankResponseData) response.getData();
        assertEquals(data.getRanking().size(), 1);
        assertEquals(response.isResult(), true);
    }
}