package com.kakaobank.placefinder.repository;

import com.kakaobank.placefinder.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByAndUserId(String userId);
}
