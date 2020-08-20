package com.kakaobank.placefinder.model;

import com.kakaobank.placefinder.entity.KeywordCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter @Setter
@Builder
public class RankResponseData {

    private List<KeywordCount> ranking;
}
