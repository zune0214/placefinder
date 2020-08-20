package com.kakaobank.placefinder.model;

import com.kakaobank.placefinder.constant.PropertyConst;
import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.exception.PlaceFinderException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class ServerResponse<T> {

    private boolean result;
    private String description;
    private T data;

    public ServerResponse(StatusCode code) {

        result = false;
        description = code.getDescription();
    }

    public ServerResponse(PlaceFinderException exception) {

        result = false;
        description = exception.getDescription();
    }

    public ServerResponse(T data) {

        result = true;
        description = PropertyConst.SUCCESS;
        this.data = data;
    }

    public static ServerResponse of(StatusCode statusCode){

        return new ServerResponse(statusCode);
    }

    public static ServerResponse of(PlaceFinderException e){

        return new ServerResponse(e);
    }

    public static ServerResponse of(Exception e){

        return new ServerResponse(StatusCode.E1500);
    }
}
