package com.tlu.khoacntt.service.impl;

import com.tlu.khoacntt.dto.request.LecturerRequest;
import com.tlu.khoacntt.dto.response.LecturerResponse;
import com.tlu.khoacntt.entity.Lecturer;
import com.tlu.khoacntt.exception.ResourceNotFoundException;
import com.tlu.khoacntt.mapper.LecturerMapper;
import com.tlu.khoacntt.repository.LecturerRepository;
import com.tlu.khoacntt.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final LecturerMapper lecturerMapper;

    @Override
    public LecturerResponse createLecturer(LecturerRequest request) {
        // 1. Kiểm tra email đã tồn tại chưa
        if (lecturerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email '" + request.getEmail() + "' đã được sử dụng bởi giảng viên khác!");
        }

        // 2. Map từ Request sang Entity
        Lecturer lecturer = lecturerMapper.toEntity(request);
        
        // 3. Lưu vào database
        Lecturer savedLecturer = lecturerRepository.save(lecturer);

        // 4. Trả về Response DTO
        return lecturerMapper.toResponse(savedLecturer);
    }

    @Override
    public LecturerResponse updateLecturer(String id, LecturerRequest request) {
        // 1. Tìm giảng viên hiện có
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giảng viên với id: " + id));

        // 2. Kiểm tra nếu thay đổi email thì email mới không được trùng với người khác
        if (!lecturer.getEmail().equalsIgnoreCase(request.getEmail()) 
                && lecturerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email mới '" + request.getEmail() + "' đã tồn tại trong hệ thống!");
        }

        // 3. Cập nhật các trường thông tin (bao gồm phone, bio, researchArea thông qua Mapper)
        lecturerMapper.updateEntity(lecturer, request);

        // 4. Lưu cập nhật
        Lecturer updatedLecturer = lecturerRepository.save(lecturer);
        
        return lecturerMapper.toResponse(updatedLecturer);
    }

    @Override
    public void deleteLecturer(String id) {
        if (!lecturerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không thể xóa! Không tìm thấy giảng viên với id: " + id);
        }
        lecturerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public LecturerResponse getLecturerById(String id) {
        return lecturerRepository.findById(id)
                .map(lecturerMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giảng viên với id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LecturerResponse> getAllLecturers() {
        return lecturerRepository.findAll().stream()
                .map(lecturerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LecturerResponse getLecturerByEmail(String email) {
        return lecturerRepository.findByEmail(email)
                .map(lecturerMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giảng viên với email: " + email));
    }
}