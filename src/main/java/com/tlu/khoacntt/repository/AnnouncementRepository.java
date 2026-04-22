package com.tlu.khoacntt.repository;

import com.tlu.khoacntt.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

    // Public: chỉ lấy published, mới nhất trước
    List<Announcement> findByStatusOrderByCreatedAtDesc(String status);

    // Lọc theo loại thông báo
    List<Announcement> findByStatusAndTypeOrderByCreatedAtDesc(String status, String type);

    // Tổng số thông báo (dùng cho dashboard)
    long countByStatus(String status);

    // Đếm tất cả (kể cả draft)
    @Query("SELECT COUNT(a) FROM Announcement a")
    long countAll();
}
