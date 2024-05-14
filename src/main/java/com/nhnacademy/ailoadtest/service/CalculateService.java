package com.nhnacademy.ailoadtest.service;

import com.nhnacademy.ailoadtest.dto.AiResponse;
import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    public String meanTemp(AiResponse aiResponse) {
        double sum = 0.0;
        int count = 0;

        for (double value : aiResponse.getPrediction()) {
            sum += value;
            count++;
        }

        double mean = sum / count;

        return Double.toString(mean);
    }

    public String kwhElect(AiResponse aiResponse) {
        double wSum = 0.0;

        for (double value : aiResponse.getPrediction()) {
            wSum += value;
        }

        double kwh = (Math.round(wSum) * 168) / 1000;
        double totalcharge = Math.round(kwh) * 114.7;

        return String.format("%.0f", totalcharge);
    }
}
