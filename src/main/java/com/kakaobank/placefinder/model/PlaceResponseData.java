package com.kakaobank.placefinder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter @Setter
@Builder
public class PlaceResponseData {

    private String searchSource;
    private String keyword;
    private Integer pageNo;
    private Integer pageSize;
    private Integer resultCount;

    private List<PlaceInfo> places;
}
