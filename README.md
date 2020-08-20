# 장소 검색 서비스
   ## 프로젝트 구성
    * Java 1.8
    * Spring Boot
    * JPA
    * H2 Embedded
   
   ## 사용된 라이브러리
    * org.apache.httpcomponents : 외부 API 호출을 위한 HttpClient 라이브러리
    * spring-boot-starter-thymeleaf : view 단 연동을 위한 타임리프 라이브러리
    * com.nimbusds.nimbus-jose-jwt : 로그인 토큰 생성/검증을 위한 JWT 라이브러리
    * com.h2database.h2 : Embedded 메모리 DB 라이브러리
    * org.projectlombok.lombok : IDE 개발 편의를 위한 LOMBOK 라이브러리
    * jQuery : VIEW 단 작업을 위한 javascript 라이브러리
    * BootStamp : VIEW 단 스타일 작업을 위한 CSS 라이브러리
  
   ## 실행하기
   * 소스 코드 빌드
   ```shell
   $ mvn clean package
   ```
   * jar 파일 실행
   ```shell
   $ java -jar placefinder-0.0.1.jar
   ```

   ## 시작하기
   * 브라우져에서 http://localhost:8080/app/login 으로 접속
   * 초기 로그인 정보는 /resource/data.sql 에 존재 (id : hello / pw : KakaoBK!)
   * 비밀번호는 SHA-256 단방향 알고리즘으로 처리하여 저장
   * http://localhost:8080/app/search 검색 페이지에서 검색
   
   ## 기능 구현
   1. 로그인 구현
      * 어플리케이션 시작시 data.sql 의 파일을 읽어 DB에 insert
      * http://localhost:8080/app/login 페이지에서 로그인 후 검색 페이지로 이동
      * http://localhost:8080/app/search 페이지는 로그인 후에 접근 가능
      * 로그인은 세션을 사용하지 않고 JWT로 검증하여 사용
   2. 장소검색
      * 카카오 API 를 사용하여 Keyword 로 장소 검색
      * 해당 데이터 조회 후 화면에 pagenation 으로 출력
         * 카카오 API에서 최대 45건 이하로만 응답함.
      * 상세 정보 화면에 출력 및 장소 이름 클릭시 kakao map 링크 열도록 함
   3. 인기 검색어
      * 검색시 마다 해당 검색어의 카운트를 업데이트함. (page 이동시에는 체크 안함)
      * keyword 카운트를 정렬하여 상위 10개를 출력함.
         * 10개 이하일 경우에는 해당 개수만큼 출력