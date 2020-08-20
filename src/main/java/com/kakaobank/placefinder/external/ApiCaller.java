package com.kakaobank.placefinder.external;

import com.kakaobank.placefinder.model.PlaceResponseData;

public interface ApiCaller {

    PlaceResponseData call(String keyword, Integer pageNo) throws Exception;
}
