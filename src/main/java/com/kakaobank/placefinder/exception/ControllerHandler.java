package com.kakaobank.placefinder.exception;

import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.model.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(PlaceFinderException.class)
    public ResponseEntity<ServerResponse> handleMoneyPlexException(PlaceFinderException e) {

        log.error("[ControllerHandler][PlaceFinderException] {}", e.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.of(e));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<ServerResponse> invalidRequest(Exception e) {

        log.error("[ControllerHandler][InvalidRequest] {}", e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServerResponse.of(StatusCode.E1400));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerResponse> handleUnauthorized(Exception e) {

        log.error("[ControllerHandler][Exception] {}", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ServerResponse.of(e));
    }
}
