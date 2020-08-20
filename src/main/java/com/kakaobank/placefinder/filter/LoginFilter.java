package com.kakaobank.placefinder.filter;

import com.kakaobank.placefinder.exception.PlaceFinderException;
import com.kakaobank.placefinder.util.LoginUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter
@Component
public class LoginFilter implements Filter {

    public static final String LOGIN_JWT_COOKIE = "X-USER-JWT";
    public static final String VIEW_CONTENT_TYPE = "text/html; charset=utf-8";
    public static final String VIEW_RESPONSE_STRING_FORMAT = "<script>alert('%s'); document.location.href='/app/login';</script>";
    public static final String API_CONTENT_TYPE = "application/json; charset=utf-8";
    public static final String API_RESPONSE_STRING_FORMAT = "{\"result\":false, \"description\":\"%s\"}";

    public static final String API_START_WITHS = "/api/v";
    public static final List<String> CHECK_URI_LIST = Arrays.asList("/app/search");

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestUri = request.getRequestURI();

        if (requestUri.startsWith(API_START_WITHS) || CHECK_URI_LIST.contains(requestUri)) {

            try {
                String cookie = getLoginJwtCookie(request.getCookies());
                LoginUtil.getLoginId(cookie);
            } catch (PlaceFinderException e) {
                errorResponse(request.getRequestURI(), (HttpServletResponse) servletResponse, e.getDescription());
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void errorResponse(
            String uri,
            HttpServletResponse response,
            String description) throws IOException {

        if (uri.startsWith(API_START_WITHS)) {
            errorResponse(response, API_CONTENT_TYPE, API_RESPONSE_STRING_FORMAT, description);
        } else {
            errorResponse(response, VIEW_CONTENT_TYPE, VIEW_RESPONSE_STRING_FORMAT, description);
        }
    }

    private void errorResponse(
            HttpServletResponse response,
            String contentType,
            String format,
            String description) throws IOException {

        response.setContentType(contentType);
        response.getWriter().print(String.format(format, description));
        response.getWriter().flush();
    }

    public String getLoginJwtCookie(Cookie[] cookies) {

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (LOGIN_JWT_COOKIE.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
