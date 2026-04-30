package com.project.fitness.dtos;

import com.project.fitness.Model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest {
    private String id;

    private String userId;

    private ActivityType type;

    private Map<String, Object> additionalMatrix;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
}
