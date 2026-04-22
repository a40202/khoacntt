package com.tlu.khoacntt.service.impl;

import com.tlu.khoacntt.dto.request.AnnouncementRequest;
import com.tlu.khoacntt.dto.response.AnnouncementResponse;
import com.tlu.khoacntt.entity.Admin;
import com.tlu.khoacntt.entity.Announcement;
import com.tlu.khoacntt.exception.ResourceNotFoundException;
import com.tlu.khoacntt.repository.AdminRepository;
import com.tlu.khoacntt.repository.AnnouncementRepository;
import com.tlu.khoacntt.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AdminRepository adminRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementResponse> getPublishedAnnouncements() {
        return announcementRepository.findByStatusOrderByCreatedAtDesc("published")
                .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementResponse> getPublishedByType(String type) {
        return announcementRepository.findByStatusAndTypeOrderByCreatedAtDesc("published", type)
                .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementResponse> getAllAnnouncements() {
        return announcementRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AnnouncementResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public AnnouncementResponse create(AnnouncementRequest request) {
        Admin admin = adminRepository.findById(request.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy admin với ID: " + request.getAdminId()));

        Announcement saved = announcementRepository.save(
            Announcement.builder()
                .title(request.getTitle().trim())
                .content(request.getContent().trim())
                .status(request.getStatus() != null ? request.getStatus() : "published")
                .type(request.getType() != null ? request.getType() : "khac")
                .admin(admin)
                .build()
        );
        return toResponse(saved);
    }

    @Override
    @Transactional
    public AnnouncementResponse update(Integer id, AnnouncementRequest request) {
        Announcement ann = findOrThrow(id);

        ann.setTitle(request.getTitle().trim());
        ann.setContent(request.getContent().trim());
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            ann.setStatus(request.getStatus());
        }
        if (request.getType() != null && !request.getType().isBlank()) {
            ann.setType(request.getType());
        }

        return toResponse(announcementRepository.save(ann));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        announcementRepository.delete(findOrThrow(id));
    }

    @Override
    public long countAll() {
        return announcementRepository.countAll();
    }

    private Announcement findOrThrow(Integer id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy thông báo với ID: " + id));
    }

    private AnnouncementResponse toResponse(Announcement a) {
        return AnnouncementResponse.builder()
                .id(a.getId())
                .title(a.getTitle())
                .content(a.getContent())
                .status(a.getStatus())
                .type(a.getType())
                .createdAt(a.getCreatedAt())
                .updatedAt(a.getUpdatedAt())
                .authorName(a.getAdmin() != null ? a.getAdmin().getFullName() : null)
                .build();
    }
}
