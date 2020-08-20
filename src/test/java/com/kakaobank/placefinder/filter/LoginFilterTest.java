package com.kakaobank.placefinder.filter;

import com.kakaobank.placefinder.util.LoginUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginFilterTest {
    private HttpServletRequest servletRequest;
    private HttpServletResponse servletResponse;
    private FilterChain filterChain;

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private BufferedOutputStream bos = new BufferedOutputStream(byteArrayOutputStream);


    private LoginFilter loginFilter;

    @BeforeEach
    void setUp() throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        bos = new BufferedOutputStream(byteArrayOutputStream);

        loginFilter = new LoginFilter();
        servletRequest = Mockito.mock(HttpServletRequest.class);
        servletResponse = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);

        Mockito.when(servletRequest.getRequestURI()).thenReturn("/api/v1/search");
        Mockito.when(servletResponse.getWriter()).thenReturn(new PrintWriter(bos));

    }

    @Test
    void doFilter() throws Exception {

        Mockito.when(servletRequest.getCookies()).thenReturn(makeCookie(LoginUtil.createJWT("hello")));
        loginFilter.doFilter(servletRequest, servletResponse, filterChain);
    }
//
    @Test
    void doFilter_fail_no_login() throws Exception {

        loginFilter.doFilter(servletRequest, servletResponse, filterChain);
        assertEquals(byteArrayOutputStream.toString(), "{\"result\":false, \"description\":\"로그인이 필요합니다\"}");
    }

    @Test
    void doFilter_fail_expire() throws Exception {

        Mockito.when(servletRequest.getCookies()).thenReturn(makeCookie("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxsbyIsImlzcyI6IlBMQUNFX0ZJTkRFUl9MT0dJTiIsImV4cCI6MTU5NzkyOTgyNX0.uNUJz-SgkZ2CWWGkQQZllhwzPZ0i2kEdMQKXTe46kzQ"));
        loginFilter.doFilter(servletRequest, servletResponse, filterChain);
        assertEquals(byteArrayOutputStream.toString(), "{\"result\":false, \"description\":\"로그인이 만료되었습니다\"}");
    }

    private Cookie[] makeCookie(String value) {

        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie(LoginFilter.LOGIN_JWT_COOKIE, value);
        return cookies;
    }

}