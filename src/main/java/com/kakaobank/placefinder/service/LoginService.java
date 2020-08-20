package com.kakaobank.placefinder.service;

import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.entity.UserInfo;
import com.kakaobank.placefinder.exception.PlaceFinderException;
import com.kakaobank.placefinder.model.LoginRequest;
import com.kakaobank.placefinder.model.RankResponseData;
import com.kakaobank.placefinder.repository.RankRepository;
import com.kakaobank.placefinder.repository.UserRepository;
import com.kakaobank.placefinder.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public StatusCode isLogin(LoginRequest login) throws PlaceFinderException {

        UserInfo userInfo = userRepository.findByAndUserId(login.getLoginId())
                .orElseThrow(() -> new PlaceFinderException(StatusCode.E1405));

        if (!userInfo.getPassword().equals(LoginUtil.passwordEncode(login.getLoginPw()))) {
            throw new PlaceFinderException(StatusCode.E1406);
        }

        return null;
    }
}