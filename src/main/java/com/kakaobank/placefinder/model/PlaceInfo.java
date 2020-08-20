package com.kakaobank.placefinder.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PlaceInfo {

    private String id;
    private String placeName;
    private String placeUrl;
    private String categoryName;
    private String addressName;
    private String roadAddressName;
    private String phone;
}