package com.nhnacademy.ailoadtest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.nhnacademy.ailoadtest.dto.AiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InfluxDBService {

    private final InfluxDBClient influxDBClient;

    public AiRequest queryData(String fluxQuery) {
        QueryApi queryApi = influxDBClient.getQueryApi();

        List<FluxTable> tables = queryApi.query(fluxQuery);

        return processData(tables);
    }

    public AiRequest processData(List<FluxTable> fluxTables) {
        List<FluxRecord> records = fluxTables.get(0).getRecords();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        AiRequest aiRequest = new AiRequest();
        List<LocalDateTime> dates = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for (FluxRecord fluxRecord : records) {
            String timeStr = String.valueOf(fluxRecord.getTime());
            timeStr = timeStr.replace('T',' ').replace('Z',' ').trim();

            if (timeStr.contains(".")){
                timeStr = timeStr.substring(0, timeStr.indexOf('.'));
            }
            LocalDateTime time = LocalDateTime.parse(timeStr, formatter);

            if (records.size() > 24){
                time = time.plusDays(7);
            }else {
                time = time.plusDays(1);
            }

            Double value = (Double) fluxRecord.getValue();

            dates.add(time);
            values.add(value);
        }

        aiRequest.setDates(dates);
        aiRequest.setValues(values);

        return aiRequest;
    }
}
