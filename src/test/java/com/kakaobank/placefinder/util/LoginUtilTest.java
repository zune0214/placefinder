package com.kakaobank.placefinder.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginUtilTest {

    private final String PASSWORD_SHA256 = "8408010995da34c0b695e38c7b1e4334657b7ccfc27f6a006611ce84af80fa5b";
    private final String PASSWORD_TEXT = "KOREA";
    @Test
    void passwordEncode() {
        assertEquals(LoginUtil.passwordEncode(PASSWORD_TEXT), PASSWORD_SHA256);
    }

    private final String LOGIN_ID = "hello";
    @Test
    void createJWT() {
        String jwt = LoginUtil.createJWT(LOGIN_ID);
        assertEquals(LoginUtil.getLoginId(jwt), LOGIN_ID);
    }
}