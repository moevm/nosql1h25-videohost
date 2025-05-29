package org.example.controller.api;

import org.example.dto.ChartType;
import org.example.dto.VideoFilter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/api/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public interface VideoStatisticsApi {

    @PostMapping("/video")
    ResponseEntity<?> getVideoStatistics(
            @RequestBody VideoFilter filter,
            @RequestParam String xAxis,
            @RequestParam String yAxis,
            @RequestParam(defaultValue = "BAR") ChartType chartType);
}
