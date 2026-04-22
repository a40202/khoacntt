package com.tlu.khoacntt.controller;

import com.tlu.khoacntt.dto.response.AnnouncementResponse;
import com.tlu.khoacntt.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/announcements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AnnouncementPublicController {

    private final AnnouncementService announcementService;

    // GET /api/public/announcements — Tất cả thông báo đã published
    @GetMapping
    public ResponseEntity<List<AnnouncementResponse>> getAll() {
        return ResponseEntity.ok(announcementService.getPublishedAnnouncements());
    }

    // GET /api/public/announcements?type=hoc-tap — Lọc theo loại
    @GetMapping(params = "type")
    public ResponseEntity<List<AnnouncementResponse>> getByType(@RequestParam String type) {
        return ResponseEntity.ok(announcementService.getPublishedByType(type));
    }

    // GET /api/public/announcements/{id} — Chi tiết thông báo
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(announcementService.getById(id));
    }
}
