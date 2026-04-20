package com.tlu.khoacntt.service;

import com.tlu.khoacntt.dto.request.LecturerRequest;
import com.tlu.khoacntt.dto.response.LecturerResponse;

import java.util.List;

public interface LecturerService {

    LecturerResponse createLecturer(LecturerRequest request);

    LecturerResponse updateLecturer(String id, LecturerRequest request);

    void deleteLecturer(String id);

    LecturerResponse getLecturerById(String id);

    List<LecturerResponse> getAllLecturers();

    // Tìm kiếm theo email (nếu cần)
    LecturerResponse getLecturerByEmail(String email);
}