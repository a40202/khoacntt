package com.tlu.khoacntt.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerResponse {
    private String id;
    private String name;
    private String email;
    private String avatar;
    private String degree;
    private String position;
    private String specialization;
    private Integer departmentId;
    private String phone;
    private String researchArea;
    private String bio;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}