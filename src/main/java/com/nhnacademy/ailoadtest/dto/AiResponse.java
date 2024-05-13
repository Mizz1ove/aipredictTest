package com.nhnacademy.ailoadtest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AiResponse {
    private List<Double> prediction;
}
