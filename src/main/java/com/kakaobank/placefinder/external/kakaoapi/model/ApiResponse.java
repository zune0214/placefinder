package com.kakaobank.placefinder.external.kakaoapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class ApiResponse {

    private Metadata meta;
    private List<Document> documents;
}
