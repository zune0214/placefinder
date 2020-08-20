package com.kakaobank.placefinder.controller;

import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.exception.PlaceFinderException;
import com.kakaobank.placefinder.service.RankService;
import com.kakaobank.placefinder.service.SearchService;
import com.kakaobank.placefinder.model.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final SearchService commonCaller;
    private final RankService rankService;

    @PostMapping("/search/{keyword}")
    public ServerResponse call(
            @PathVariable("keyword") String keyword) throws Exception {

        return call(keyword, 1);
    }

    @PostMapping("/search/{keyword}/{pageNo}")
    public ServerResponse call(
            @PathVariable("keyword") String keyword,
            @PathVariable("pageNo") Integer pageNo) throws Exception {

        if (keyword == null || keyword.length() < 1) {
            throw new PlaceFinderException(StatusCode.E1402);
        }

        return new ServerResponse(commonCaller.call(keyword, pageNo));
    }

    @GetMapping("/keywordRank")
    public ServerResponse keywordRank() {

        return new ServerResponse(rankService.getPopularKeyword());
    }
}
