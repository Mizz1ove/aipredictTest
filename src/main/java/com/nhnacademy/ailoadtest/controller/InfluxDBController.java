package com.nhnacademy.ailoadtest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.ailoadtest.dto.AiRequest;
import com.nhnacademy.ailoadtest.service.InfluxDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InfluxDBController {
    private final InfluxDBService influxDBService;

    @GetMapping("/data")
    public AiRequest getData() throws JsonProcessingException {
        String fluxQuery = "from(bucket: \"raw_data\")\n" +
                "  |> range(start: -7d)\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"nhnacademy\")\n" +
                "  |> filter(fn: (r) => r[\"branch\"] == \"gyeongnam\")\n" +
                "  |> filter(fn: (r) => r[\"endpoint\"] == \"temperature\")\n" +
                "  |> filter(fn: (r) => r[\"place\"] == \"class_a\" or r[\"place\"] == \"class_b\" or r[\"place\"] == \"lobby\" or r[\"place\"] == \"meeting_room\" or r[\"place\"] == \"office\" or r[\"place\"] == \"server_room\" or r[\"place\"] == \"storage\")\n" +
                "  |> group(columns: [\"site\"])\n" +
                "  |> aggregateWindow(every: 1h, fn: mean, createEmpty: false)\n" +
                "  |> yield(name: \"mean\")";

        return influxDBService.queryData(fluxQuery);
    }
}
