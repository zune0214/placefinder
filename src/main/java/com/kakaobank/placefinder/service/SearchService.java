package com.kakaobank.placefinder.service;

import com.kakaobank.placefinder.constant.PropertyConst;
import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.entity.KeywordCount;
import com.kakaobank.placefinder.exception.PlaceFinderException;
import com.kakaobank.placefinder.external.ApiCaller;
import com.kakaobank.placefinder.model.PlaceResponseData;
import com.kakaobank.placefinder.repository.RankRepository;
import com.kakaobank.placefinder.util.SpringContextBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final RankRepository rankRepository;

    @Value("${search.source.type}")
    private String type;

    private ApiCaller getCaller() {

        return (ApiCaller) SpringContextBean.getBean(PropertyConst.API_CLIENT + type);
    }

    public PlaceResponseData call(
            final String keyword,
            final Integer pageNo) throws Exception {

        if (pageNo.equals(1)) {
            updateKeywordCount(keyword);
        }

        return getCaller().call(keyword, pageNo);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Async
    public void updateKeywordCount(String keyword) throws PlaceFinderException {

        try {

            KeywordCount keywordCount =
                    rankRepository.findByKeyword(keyword).orElse(KeywordCount.of(keyword));
            keywordCount.plus();
            rankRepository.save(keywordCount);
            log.info("FIRST KEYWORD COUNT {} : {}", keyword, keywordCount.getCount());

        } catch (DataIntegrityViolationException ex) {
            throw new PlaceFinderException(StatusCode.E1501, ex);
        } catch (Exception ex) {
            throw new PlaceFinderException(StatusCode.E1500, ex);
        }
    }
}
