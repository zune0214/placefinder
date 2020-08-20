package com.kakaobank.placefinder.external.kakaoapi.model;

import com.kakaobank.placefinder.model.PlaceInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Document {

    private String place_name;
    private String distance;
    private String place_url;
    private String category_name;
    private String address_name;
    private String road_address_name;
    private String id;
    private String phone;
    private String category_group_code;
    private String category_group_name;
    private String x;
    private String y;

    public PlaceInfo toPlaceInfo() {

        return PlaceInfo.builder()
                .addressName(address_name)
                .categoryName(category_name)
                .id(id)
                .phone(phone)
                .placeName(place_name)
                .placeUrl(place_url)
                .roadAddressName(road_address_name)
                .build();
    }
}
