package com.kakaobank.placefinder.external.kakaoapi;

import com.kakaobank.placefinder.constant.PropertyConst;
import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.exception.PlaceFinderException;
import com.kakaobank.placefinder.external.ApiCaller;
import com.kakaobank.placefinder.external.kakaoapi.model.ApiResponse;
import com.kakaobank.placefinder.model.PlaceInfo;
import com.kakaobank.placefinder.model.PlaceResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component("API_CLIENT_KAKAO")
@RequiredArgsConstructor
@Slf4j
public class ApiClient implements ApiCaller {
    private static final int MAX_PAGE = 3;
    private static final String SEARCH_SOURCE = "kakao";
    private static final String URL_FORMAT = "%s?query=%s&page=%d";
    private final RestTemplate restTemplate;

    @Value("${kakao.authorization}")
    private String authorization;
    @Value("${kakao.baseUrl}")
    private String baseUrl;

    @Override
    public PlaceResponseData call(String keyword, Integer pageNo) throws PlaceFinderException {

        if (pageNo < 1 || MAX_PAGE < pageNo) {
            throw new PlaceFinderException(StatusCode.E1401);
        }

        ApiResponse response;
        List<PlaceInfo> places;
        try {

            String url = String.format(URL_FORMAT, baseUrl, keyword, pageNo);
            ResponseEntity<ApiResponse> responseEntity =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            makeHttpEntity(),
                            ApiResponse.class);

            if (responseEntity.getStatusCode() != HttpStatus.OK){
                throw new PlaceFinderException(responseEntity.getStatusCode());
            }

            log.info("status " + responseEntity.getStatusCode());
            response = responseEntity.getBody();

            places = response.getDocuments()
                    .stream()
                    .map(document -> document.toPlaceInfo())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new PlaceFinderException(StatusCode.E1500, e);
        }

        return PlaceResponseData.builder()
                .searchSource(SEARCH_SOURCE)
                .keyword(keyword)
                .pageNo(pageNo)
                .pageSize(response.getMeta().getPageable_count())
                .resultCount(response.getMeta().getTotal_count())
                .places(places)
                .build();
    }

    private HttpEntity makeHttpEntity() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(PropertyConst.AUTHORIZATION, authorization);
        return new HttpEntity(httpHeaders);
    }
}
