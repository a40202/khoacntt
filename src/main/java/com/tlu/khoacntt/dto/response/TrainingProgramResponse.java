package com.tlu.khoacntt.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingProgramResponse {
    private Integer id;
    private String name;
    private String description;
    private String careerOrientation;
    private Integer majorId;
    private String majorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String duration;
    private String level;
    private List<CourseResponse> courses;
}
