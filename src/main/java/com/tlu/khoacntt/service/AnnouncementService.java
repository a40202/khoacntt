package com.tlu.khoacntt.service;

import com.tlu.khoacntt.dto.request.AnnouncementRequest;
import com.tlu.khoacntt.dto.response.AnnouncementResponse;

import java.util.List;

public interface AnnouncementService {
    List<AnnouncementResponse> getPublishedAnnouncements();
    List<AnnouncementResponse> getPublishedByType(String type);
    List<AnnouncementResponse> getAllAnnouncements();
    AnnouncementResponse getById(Integer id);
    AnnouncementResponse create(AnnouncementRequest request);
    AnnouncementResponse update(Integer id, AnnouncementRequest request);
    void delete(Integer id);
    long countAll();
}
