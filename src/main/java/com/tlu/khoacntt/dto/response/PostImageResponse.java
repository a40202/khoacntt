package com.tlu.khoacntt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostImageResponse {
    private Integer imageId;
    private String imageUrl;
    private LocalDateTime createdAt;
    private Integer postId;
}
