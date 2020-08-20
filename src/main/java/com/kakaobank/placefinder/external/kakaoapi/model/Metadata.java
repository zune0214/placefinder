package com.kakaobank.placefinder.external.kakaoapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Metadata {
    private Integer total_count;
    private Integer pageable_count;
    private Boolean is_end;
}
