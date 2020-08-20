package com.kakaobank.placefinder.controller;

import com.kakaobank.placefinder.exception.PlaceFinderException;
import com.kakaobank.placefinder.filter.LoginFilter;
import com.kakaobank.placefinder.model.LoginRequest;
import com.kakaobank.placefinder.model.LoginResponse;
import com.kakaobank.placefinder.model.ServerResponse;
import com.kakaobank.placefinder.service.LoginService;
import com.kakaobank.placefinder.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login/loginChecker")
    public ServerResponse loginChecker(HttpServletResponse response, @RequestBody LoginRequest loginRequest) throws PlaceFinderException {

        loginRequest.validator();
        loginService.isLogin(loginRequest);

        String loginToken = LoginUtil.createJWT(loginRequest.getLoginId());
        Cookie cookie = new Cookie(LoginFilter.LOGIN_JWT_COOKIE, loginToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ServerResponse(LoginResponse.of(loginToken));
    }
}