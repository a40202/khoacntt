package com.tlu.khoacntt.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MajorResponse {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
    private List<TrainingProgramResponse> trainingPrograms;
}
