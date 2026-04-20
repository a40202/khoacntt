package com.tlu.khoacntt.service.impl;

import com.tlu.khoacntt.dto.response.CourseResponse;
import com.tlu.khoacntt.dto.response.MajorResponse;
import com.tlu.khoacntt.dto.response.TrainingProgramResponse;
import com.tlu.khoacntt.entity.Course;
import com.tlu.khoacntt.entity.Major;
import com.tlu.khoacntt.entity.TrainingProgram;
import com.tlu.khoacntt.exception.ResourceNotFoundException;
import com.tlu.khoacntt.repository.MajorRepository;
import com.tlu.khoacntt.repository.TrainingProgramRepository;
import com.tlu.khoacntt.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingProgramServiceImpl implements TrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;
    private final MajorRepository majorRepository;

    // Lấy tất cả chương trình đào tạo kèm courses và thông tin major
    @Override
    @Transactional(readOnly = true)
    public List<TrainingProgramResponse> getAllForPublic() {
        return trainingProgramRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // Lấy chi tiết 1 chương trình đào tạo theo ID (kèm courses + major)
    @Override
    @Transactional(readOnly = true)
    public TrainingProgramResponse getById(Integer id) {
        TrainingProgram entity = trainingProgramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chương trình đào tạo id: " + id));
        return toResponse(entity);
    }

    // Lấy tất cả majors kèm danh sách training programs (không kèm courses)
    @Override
    @Transactional(readOnly = true)
    public List<MajorResponse> getAllMajors() {
        return majorRepository.findAll().stream()
                .map(this::toMajorResponse)
                .toList();
    }

    // Lấy chi tiết 1 major theo ID (kèm training programs + courses)
    @Override
    @Transactional(readOnly = true)
    public MajorResponse getMajorById(Integer id) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ngành học id: " + id));
        return toMajorResponse(major);
    }

    // ===== Mapper helpers =====

    private TrainingProgramResponse toResponse(TrainingProgram e) {
        List<CourseResponse> courses = e.getCourses() != null
                ? e.getCourses().stream().map(this::toCourseResponse).toList()
                : Collections.emptyList();

        return TrainingProgramResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .careerOrientation(e.getCareerOrientation())
                .majorId(e.getMajor() != null ? e.getMajor().getId() : null)
                .majorName(e.getMajor() != null ? e.getMajor().getName() : null)
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .duration(e.getDuration())
                .level(e.getLevel())
                .courses(courses)
                .build();
    }

    private CourseResponse toCourseResponse(Course c) {
        return CourseResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .createdAt(c.getCreatedAt())
                .build();
    }

    private MajorResponse toMajorResponse(Major m) {
        List<TrainingProgramResponse> programs = m.getTrainingPrograms() != null
                ? m.getTrainingPrograms().stream().map(this::toResponse).toList()
                : Collections.emptyList();

        return MajorResponse.builder()
                .id(m.getId())
                .name(m.getName())
                .createdAt(m.getCreatedAt())
                .trainingPrograms(programs)
                .build();
    }
}
