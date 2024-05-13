package com.nhnacademy.ailoadtest.service;

import com.nhnacademy.ailoadtest.dto.AiRequest;
import com.nhnacademy.ailoadtest.dto.AiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PredictService {
    @Autowired
    private RestTemplate restTemplate;

    private String predictUrl = "http://125.6.36.234:5000";

    public AiResponse predictTemp(AiRequest aiRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AiRequest> request  = new HttpEntity<>(aiRequest, httpHeaders);
        ResponseEntity<AiResponse> exchange = restTemplate.exchange(
                predictUrl + "/predict/temp",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        return exchange.getBody();
    }

    public AiResponse predictElect(AiRequest aiRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AiRequest> request  = new HttpEntity<>(aiRequest, httpHeaders);
        ResponseEntity<AiResponse> exchange = restTemplate.exchange(
                predictUrl + "/predict/elect",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<AiResponse>() {}
        );

        return exchange.getBody();
    }
}
