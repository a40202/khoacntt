package com.tlu.khoacntt.dto.response;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DashboardStatsResponse {
    private long totalPosts;
    private long totalFaculty;
    private long totalCategories;
    private long totalAnnouncements;
    private long totalViews;
}
