package com.tlu.khoacntt.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Integer id;
    private String title;
    private String slug;
    private String content;
    private String thumbnail;
    private String status;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CategoryInfo category;
    private AuthorInfo author;
    private List<PostImageResponse> images;

    @Data
    @AllArgsConstructor
    public static class CategoryInfo {
        private Integer id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    public static class AuthorInfo {
        private String id;
        private String fullName;
    }
}