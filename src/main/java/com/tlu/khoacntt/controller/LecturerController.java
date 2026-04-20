package com.tlu.khoacntt.controller;

import com.tlu.khoacntt.dto.request.LecturerRequest;
import com.tlu.khoacntt.dto.response.LecturerResponse;
import com.tlu.khoacntt.service.LecturerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/lecturers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LecturerController {

    private final LecturerService lecturerService;

    // 1. Lấy danh sách tất cả giảng viên
    @GetMapping
    public ResponseEntity<List<LecturerResponse>> getAllLecturers() {
        List<LecturerResponse> lecturers = lecturerService.getAllLecturers();
        return ResponseEntity.ok(lecturers);
    }

    // 2. Lấy chi tiết một giảng viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<LecturerResponse> getLecturerById(@PathVariable String id) {
        return ResponseEntity.ok(lecturerService.getLecturerById(id));
    }

    // 3. Thêm mới giảng viên
    @PostMapping
    public ResponseEntity<LecturerResponse> createLecturer(@Valid @RequestBody LecturerRequest request) {
        LecturerResponse newLecturer = lecturerService.createLecturer(request);
        return new ResponseEntity<>(newLecturer, HttpStatus.CREATED);
    }

    // 4. Cập nhật thông tin giảng viên
    @PutMapping("/{id}")
    public ResponseEntity<LecturerResponse> updateLecturer(
            @PathVariable String id, 
            @Valid @RequestBody LecturerRequest request) {
        return ResponseEntity.ok(lecturerService.updateLecturer(id, request));
    }

    // 5. Xóa giảng viên
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecturer(@PathVariable String id) {
        lecturerService.deleteLecturer(id);
        return ResponseEntity.noContent().build();
    }
}