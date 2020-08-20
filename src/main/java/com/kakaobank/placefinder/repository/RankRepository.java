package com.kakaobank.placefinder.repository;

import com.kakaobank.placefinder.entity.KeywordCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RankRepository extends JpaRepository<KeywordCount, Long> {

    Optional<KeywordCount> findByKeyword(String keyword);
    List<KeywordCount> findTop10ByOrderByCountDescKeywordAsc();
}
