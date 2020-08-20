package com.kakaobank.placefinder.external.kakaoapi;

import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.exception.PlaceFinderException;
import com.kakaobank.placefinder.model.PlaceResponseData;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiClientTest {
    @Autowired
    private ApiClient apiClient;

    @Test
    void call() {
        PlaceResponseData placeResponseData = apiClient.call("이마트", 1);
        assertNotNull(placeResponseData);
    }

    @Test
    void call_page_fail() {
        try{
            PlaceResponseData placeResponseData = apiClient.call("이마트", 0);
        }catch(PlaceFinderException e){
            assertEquals(e.getResultCode(), StatusCode.E1401.getCode());
        }
    }
}