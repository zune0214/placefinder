package com.kakaobank.placefinder.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {
    private String loginJwt;

    public static LoginResponse of(String loginJwt) {

        return new LoginResponse(loginJwt);
    }
}
