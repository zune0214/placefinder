package com.kakaobank.placefinder.service;

import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.exception.PlaceFinderException;
import com.kakaobank.placefinder.model.LoginRequest;
import com.kakaobank.placefinder.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginServiceTest {
    @Autowired
    private LoginService loginService;

    @Test
    void isLogin_test() throws PlaceFinderException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLoginId("hello");
        loginRequest.setLoginPw("KakaoBK!");
        StatusCode result = loginService.isLogin(loginRequest);
        assertEquals(result, null);
    }

    @Test
    void isLogin_fail_nouser() throws PlaceFinderException {

        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setLoginId("hello2");
            loginRequest.setLoginPw("KakaoBK!");
            loginService.isLogin(loginRequest);
        } catch (PlaceFinderException e){
            assertEquals(e.getResultCode(), StatusCode.E1405.getCode());
        }
    }

    @Test
    void isLogin_fail_pw_nomatch() throws PlaceFinderException {

        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setLoginId("hello");
            loginRequest.setLoginPw("s!");
            loginService.isLogin(loginRequest);
        } catch (PlaceFinderException e){
            assertEquals(e.getResultCode(), StatusCode.E1406.getCode());
        }
    }
}