package com.project.fitness.service;

import com.project.fitness.Model.Activity;
import com.project.fitness.Model.User;
import com.project.fitness.dtos.ActivityRequest;
import com.project.fitness.dtos.ActivityResponse;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {

        String userId = activityRequest.getUserId();

        if (userId == null || userId.isBlank()) {
            throw new RuntimeException("UserId is required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Invalid user :" +activityRequest.getUserId()));


        Activity activity = Activity.builder()
                .user(user)
                .type(activityRequest.getType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .additionalMatrix(activityRequest.getAdditionalMatrix())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity savedActivity) {
        ActivityResponse response = new ActivityResponse();

        response.setId(savedActivity.getId());
        response.setUserId(savedActivity.getUser().getId());
        response.setType(savedActivity.getType());
        response.setDuration(savedActivity.getDuration());
        response.setCaloriesBurned(savedActivity.getCaloriesBurned());
        response.setStartTime(savedActivity.getStartTime());
        response.setAdditionalMatrix(savedActivity.getAdditionalMatrix());
        response.setCreatedAt(savedActivity.getCreatedAt());
        response.setUpdatedAt(savedActivity.getUpdatedAt());

        return response;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activityList = activityRepository.findByUserId(userId);
        return activityList.stream()
                .map(this :: mapToResponse)
                .collect(Collectors.toList());
    }
}
