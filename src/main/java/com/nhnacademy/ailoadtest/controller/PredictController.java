package com.nhnacademy.ailoadtest.controller;

import com.nhnacademy.ailoadtest.dto.AiResponse;
import com.nhnacademy.ailoadtest.service.InfluxDBService;
import com.nhnacademy.ailoadtest.service.PredictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/predict")
public class PredictController {
    private final PredictService predictService;
    private final InfluxDBService influxDBService;

    @PostMapping("/temp")
    public AiResponse predictTemp() {
        String fluxQuery = "from(bucket: \"raw_data\")\n" +
                "  |> range(start: -7d)\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"nhnacademy\")\n" +
                "  |> filter(fn: (r) => r[\"branch\"] == \"gyeongnam\")\n" +
                "  |> filter(fn: (r) => r[\"endpoint\"] == \"temperature\")\n" +
                "  |> filter(fn: (r) => r[\"place\"] == \"class_a\" or r[\"place\"] == \"class_b\" or r[\"place\"] == \"lobby\" or r[\"place\"] == \"meeting_room\" or r[\"place\"] == \"office\" or r[\"place\"] == \"server_room\" or r[\"place\"] == \"storage\")\n" +
                "  |> group(columns: [\"site\"])\n" +
                "  |> aggregateWindow(every: 1h, fn: mean, createEmpty: false)\n" +
                "  |> yield(name: \"mean\")";

        return predictService.predictTemp(influxDBService.queryData(fluxQuery));
    }

    @PostMapping("/elect")
    public AiResponse predictElect() {
        String fluxQuery = "from(bucket: \"raw_data\")\n" +
                "  |> range(start: -7d)\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"nhnacademy\")\n" +
                "  |> filter(fn: (r) => r[\"branch\"] == \"gyeongnam\")\n" +
                "  |> filter(fn: (r) => r[\"endpoint\"] == \"electrical_energy\")\n" +
                "  |> filter(fn: (r) => r[\"description\"] == \"w\")\n" +
                "  |> filter(fn: (r) => r[\"phase\"] == \"total\")\n" +
                "  |> group(columns: [\"_measurement\", \"branch\", \"endpoint\", \"description\", \"phase\", \"site\"])\n" +
                "  |> aggregateWindow(every: 2m, fn: mean, createEmpty: false)\n" +
                "  |> yield(name: \"mean\")";

        return predictService.predictTemp(influxDBService.queryData(fluxQuery));
    }
}
