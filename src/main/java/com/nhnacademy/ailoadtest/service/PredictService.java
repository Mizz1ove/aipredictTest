package com.nhnacademy.ailoadtest.service;

import com.nhnacademy.ailoadtest.dto.AiRequest;
import com.nhnacademy.ailoadtest.dto.AiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class PredictService {

    private final RestTemplate restTemplate;

    private final String predictUrl = "http://125.6.36.234:5000";

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
