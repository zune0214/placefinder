package com.kakaobank.placefinder.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "unique_keyword", columnList = "keyword", unique = true)
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeywordCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private Long count;

    public void plus() {

        count++;
    }

    public static KeywordCount of(String keyword) {

        KeywordCount keywordCount = new KeywordCount();
        keywordCount.setKeyword(keyword);
        keywordCount.setCount(0l);
        return keywordCount;
    }

}
