package com.kakaobank.placefinder.constant;

public enum StatusCode {

    E1400(1400, "잘못된 요청 입니다"),

    E1401(1401, "페이지는 1이상 3이하여야 합니다"),
    E1402(1402, "키워드를 입력하세요"),

    E1403(1403, "아이디를 입력하세요"),
    E1404(1404, "비밀번호를 입력하세요"),
    E1405(1405, "등록된 사용자가 아닙니다"),
    E1406(1406, "비밀번호가 일치하지 않습니다"),

    E1410(1410, "로그인이 만료되었습니다"),
    E1411(1411, "유효하지 않은 토큰입니다"),
    E1412(1412, "로그인이 필요합니다"),

    E1500(1500, "서버 오류"),
    E1501(1501, "DB 오류 - constraint violation"),
    E1502(1502, "로그인 토큰 생성 실패"),
    E1503(1503, "로그인 토큰 검증 실패"),

    E1510(1510, "외부 서버와의 연결에 실패하였습니다")
    ;

    private int code;
    private String description;

    StatusCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
