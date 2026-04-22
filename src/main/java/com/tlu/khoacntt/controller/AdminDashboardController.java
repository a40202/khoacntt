package com.tlu.khoacntt.controller;

import com.tlu.khoacntt.dto.response.ChartDataResponse;
import com.tlu.khoacntt.dto.response.DashboardStatsResponse;
import com.tlu.khoacntt.repository.AnnouncementRepository;
import com.tlu.khoacntt.repository.CategoryRepository;
import com.tlu.khoacntt.repository.LecturerRepository;
import com.tlu.khoacntt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminDashboardController {

    private final PostRepository postRepository;
    private final LecturerRepository lecturerRepository;
    private final CategoryRepository categoryRepository;
    private final AnnouncementRepository announcementRepository;

    // GET /api/admin/dashboard/stats
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsResponse> getStats() {
        DashboardStatsResponse stats = DashboardStatsResponse.builder()
                .totalPosts(postRepository.count())
                .totalFaculty(lecturerRepository.count())
                .totalCategories(categoryRepository.count())
                .totalAnnouncements(announcementRepository.countAll())
                .totalViews(postRepository.sumAllViewCounts())
                .build();
        return ResponseEntity.ok(stats);
    }

    // GET /api/admin/dashboard/chart — Thống kê bài viết 6 tháng gần nhất
    @GetMapping("/chart")
    public ResponseEntity<ChartDataResponse> getChartData() {
        // Lấy đầu tháng của 6 tháng trước
        LocalDateTime from = LocalDateTime.now()
                .minusMonths(5)
                .withDayOfMonth(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        // Query DB: trả về [year, month, count]
        List<Object[]> rows = postRepository.countPostsGroupedByMonth(from);

        // Map kết quả vào dạng year-month → count
        Map<String, Long> dataMap = new HashMap<>();
        for (Object[] row : rows) {
            int year  = ((Number) row[0]).intValue();
            int month = ((Number) row[1]).intValue();
            long count = ((Number) row[2]).longValue();
            dataMap.put(year + "-" + String.format("%02d", month), count);
        }

        // Tạo đủ 6 tháng liên tiếp (tháng nào không có bài → 0)
        List<String> labels = new ArrayList<>();
        List<Long> counts = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");

        for (int i = 0; i < 6; i++) {
            LocalDateTime month = from.plusMonths(i);
            String key = month.getYear() + "-" + String.format("%02d", month.getMonthValue());
            labels.add(month.format(fmt));
            counts.add(dataMap.getOrDefault(key, 0L));
        }

        return ResponseEntity.ok(ChartDataResponse.builder()
                .labels(labels)
                .postCounts(counts)
                .build());
    }
}
