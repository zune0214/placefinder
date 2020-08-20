package com.kakaobank.placefinder.model;

import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.exception.PlaceFinderException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    private String loginId;
    private String loginPw;

    public StatusCode validator() throws PlaceFinderException {

        if (loginId == null || loginId.length() < 1) {
            throw new PlaceFinderException(StatusCode.E1403);
        }

        if (loginPw == null || loginPw.length() < 1) {
            throw new PlaceFinderException(StatusCode.E1404);
        }

        return null;
    }
}
