package com.kakaobank.placefinder.controller;

import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.exception.PlaceFinderException;
import com.kakaobank.placefinder.model.LoginRequest;
import com.kakaobank.placefinder.model.LoginResponse;
import com.kakaobank.placefinder.model.ServerResponse;
import com.kakaobank.placefinder.service.LoginService;
import com.kakaobank.placefinder.util.LoginUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {
    @Autowired
    private LoginService loginService;
    private LoginController loginController;
    private LoginRequest loginRequest;
    @Mock
    private HttpServletResponse httpServletResponse;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginController = new LoginController(loginService);
    }

    @Test
    void loginChecker() {
        loginRequest.setLoginId("hello");
        loginRequest.setLoginPw("KakaoBK!");
        ServerResponse response = loginController.loginChecker(httpServletResponse, loginRequest);
        assertEquals(response.isResult(), true);
        assertEquals(response.getData().getClass(), LoginResponse.class);
        LoginResponse loginResponse = (LoginResponse) response.getData();
        assertEquals(LoginUtil.getLoginId(loginResponse.getLoginJwt()), "hello");
    }

    @Test
    void loginChecker_fail_id_null() {
        try{
            loginRequest.setLoginId(null);
            loginController.loginChecker(httpServletResponse, loginRequest);
        }catch(PlaceFinderException e){
            assertEquals(e.getResultCode(), StatusCode.E1403.getCode());
        }
    }

    @Test
    void loginChecker_fail_pw_null() {
        try{
            loginRequest.setLoginPw(null);
            loginController.loginChecker(httpServletResponse, loginRequest);
        }catch(PlaceFinderException e){
            assertEquals(e.getResultCode(), StatusCode.E1403.getCode());
        }
    }

    @Test
    void loginChecker_fail_nouser() {
        try{
            loginRequest.setLoginId("hi");
            loginRequest.setLoginPw("hi");
            loginController.loginChecker(httpServletResponse, loginRequest);
        }catch(PlaceFinderException e){
            assertEquals(e.getResultCode(), StatusCode.E1405.getCode());
        }
    }

    @Test
    void loginChecker_fail_pw_nomatch() {
        try{
            loginRequest.setLoginId("hello");
            loginRequest.setLoginPw("hi");
            loginController.loginChecker(httpServletResponse, loginRequest);
        }catch(PlaceFinderException e){
            assertEquals(e.getResultCode(), StatusCode.E1406.getCode());
        }
    }
}