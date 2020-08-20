package com.kakaobank.placefinder.service;

import com.kakaobank.placefinder.model.RankResponseData;
import com.kakaobank.placefinder.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankService {
    private final RankRepository rankRepository;

    public RankResponseData getPopularKeyword() {

        return RankResponseData.builder()
                .ranking(rankRepository.findTop10ByOrderByCountDescKeywordAsc())
                .build();
    }
}