package com.tlu.khoacntt.repository;

import com.tlu.khoacntt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    // Tìm bài viết theo đường dẫn tĩnh (Slug) - Dùng cho User xem chi tiết
    Optional<Post> findBySlug(String slug);

    //  Tìm bài viết theo trạng thái (vd: "published", "draft")
    List<Post> findByStatus(String status);

    //  Tìm bài viết theo ID danh mục (Sử dụng liên kết trong Entity Post)
    List<Post> findByCategory_Id(Integer categoryId);

    //  Tìm kiếm bài viết theo tiêu đề (Không phân biệt hoa thường)
    List<Post> findByTitleContainingIgnoreCase(String title);
    List<Post> findByStatusAndTitleContainingIgnoreCase(String status, String title);

    // Query thủ công để tăng lượt xem (Tối ưu hiệu năng hơn là save toàn bộ Entity)
    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Integer id);

    //  Kiểm tra sự tồn tại của slug (Dùng khi tạo mới bài viết để tránh trùng URL)
    boolean existsBySlug(String slug);
}