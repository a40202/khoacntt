package com.tlu.khoacntt.mapper;

import com.tlu.khoacntt.dto.request.LecturerRequest;
import com.tlu.khoacntt.dto.response.LecturerResponse;
import com.tlu.khoacntt.entity.Lecturer;
import org.springframework.stereotype.Component;

@Component
public class LecturerMapper {

    /**
     * Chuyển từ Request (nhận từ client) → Entity (lưu vào DB)
     */
    public Lecturer toEntity(LecturerRequest request) {
        if (request == null) {
            return null;
        }

        return Lecturer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .avatar(request.getAvatar())
                .degree(request.getDegree())
                .position(request.getPosition())
                .specialization(request.getSpecialization())
                .departmentId(request.getDepartmentId())
                .phone(request.getPhone())          
                .researchArea(request.getResearchArea()) 
                .bio(request.getBio())              
                .build();
    }

    /**
     * Chuyển từ Entity → Response (trả về cho client)
     */
    public LecturerResponse toResponse(Lecturer lecturer) {
        if (lecturer == null) {
            return null;
        }

        return LecturerResponse.builder()
                .id(lecturer.getId())
                .name(lecturer.getName())
                .email(lecturer.getEmail())
                .avatar(lecturer.getAvatar())
                .degree(lecturer.getDegree())
                .position(lecturer.getPosition())
                .specialization(lecturer.getSpecialization())
                .departmentId(lecturer.getDepartmentId())
                .phone(lecturer.getPhone())          
                .researchArea(lecturer.getResearchArea()) 
                .bio(lecturer.getBio())              
                .createdAt(lecturer.getCreatedAt())
                .updatedAt(lecturer.getUpdatedAt())
                .build();
    }

    /**
     * Cập nhật thông tin từ Request vào Entity hiện có (dùng cho Update)
     */
    public void updateEntity(Lecturer lecturer, LecturerRequest request) {
        if (request == null || lecturer == null) {
            return;
        }

        lecturer.setName(request.getName());
        lecturer.setEmail(request.getEmail());
        lecturer.setAvatar(request.getAvatar());
        lecturer.setDegree(request.getDegree());
        lecturer.setPosition(request.getPosition());
        lecturer.setSpecialization(request.getSpecialization());
        lecturer.setDepartmentId(request.getDepartmentId());
        lecturer.setPhone(request.getPhone());               
        lecturer.setResearchArea(request.getResearchArea());   
        lecturer.setBio(request.getBio());                      
    }
}