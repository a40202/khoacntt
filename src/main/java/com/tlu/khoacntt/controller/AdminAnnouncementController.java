package com.tlu.khoacntt.controller;

import com.tlu.khoacntt.dto.request.AnnouncementRequest;
import com.tlu.khoacntt.dto.response.AnnouncementResponse;
import com.tlu.khoacntt.service.AnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/announcements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminAnnouncementController {

    private final AnnouncementService announcementService;

    // GET /api/admin/announcements — Tất cả (kể cả draft)
    @GetMapping
    public ResponseEntity<List<AnnouncementResponse>> getAll() {
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }

    // GET /api/admin/announcements/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(announcementService.getById(id));
    }

    // POST /api/admin/announcements — Tạo thông báo mới
    @PostMapping
    public ResponseEntity<AnnouncementResponse> create(@Valid @RequestBody AnnouncementRequest request) {
        return new ResponseEntity<>(announcementService.create(request), HttpStatus.CREATED);
    }

    // PUT /api/admin/announcements/{id} — Sửa thông báo
    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementResponse> update(
            @PathVariable Integer id,
            @Valid @RequestBody AnnouncementRequest request
    ) {
        return ResponseEntity.ok(announcementService.update(id, request));
    }

    // DELETE /api/admin/announcements/{id} — Xóa thông báo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        announcementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
