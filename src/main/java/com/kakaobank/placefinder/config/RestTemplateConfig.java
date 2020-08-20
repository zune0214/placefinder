package com.kakaobank.placefinder.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${resttemplate.http.readTimeout}")
    private int readTimeout;
    @Value("${resttemplate.http.connectionTimeout}")
    private int connectionTimeout;

    @Value("${resttemplate.http.maxConn}")
    private int maxConn;
    @Value("${resttemplate.http.maxConnPerRoute}")
    private int maxConnPerRoute;

    @Bean
    public RestTemplate getRestTemplate() {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(readTimeout);
        factory.setConnectionRequestTimeout(connectionTimeout);

        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(maxConn)
                .setMaxConnPerRoute(maxConnPerRoute).build();

        factory.setHttpClient(httpClient);
        return new RestTemplate(factory);
    }
}
