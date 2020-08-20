package com.kakaobank.placefinder.exception;

import com.kakaobank.placefinder.constant.StatusCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
public class PlaceFinderException extends RuntimeException {

    private String description;
    private int resultCode;

    public PlaceFinderException(HttpStatus statusCode) {

        this.resultCode = 1000 + statusCode.value();
        this.description = StatusCode.E1510.getDescription() + " - " + statusCode.name();
    }

    public PlaceFinderException(StatusCode statusCode) {

        this.resultCode = statusCode.getCode();
        this.description = statusCode.getDescription();
    }

    public PlaceFinderException(StatusCode statusCode, Exception e) {

        this.resultCode = statusCode.getCode();
        this.description = statusCode.getDescription();
        log.error("Exception", e);
    }
}
