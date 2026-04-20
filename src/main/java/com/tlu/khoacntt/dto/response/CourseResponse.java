package com.tlu.khoacntt.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
