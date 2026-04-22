package com.tlu.khoacntt.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AnnouncementResponse {
    private Integer id;
    private String title;
    private String content;
    private String status;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String authorName;
}
