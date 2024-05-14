package com.nhnacademy.ailoadtest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AiRequest {
    private List<String> dates;
    private List<Double> values;
}
