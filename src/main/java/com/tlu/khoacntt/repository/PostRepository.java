package com.tlu.khoacntt.repository;

import com.tlu.khoacntt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> findBySlug(String slug);

    List<Post> findByStatus(String status);

    List<Post> findByCategory_Id(Integer categoryId);

    List<Post> findByTitleContainingIgnoreCase(String title);
    List<Post> findByStatusAndTitleContainingIgnoreCase(String status, String title);

    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Integer id);

    boolean existsBySlug(String slug);

    // Tổng lượt xem toàn bộ bài viết
    @Query("SELECT COALESCE(SUM(p.viewCount), 0) FROM Post p")
    long sumAllViewCounts();

    // Đếm bài viết theo từng tháng trong khoảng thời gian 
    @Query("""
        SELECT YEAR(p.createdAt), MONTH(p.createdAt), COUNT(p)
        FROM Post p
        WHERE p.createdAt >= :from
        GROUP BY YEAR(p.createdAt), MONTH(p.createdAt)
        ORDER BY YEAR(p.createdAt), MONTH(p.createdAt)
        """)
    List<Object[]> countPostsGroupedByMonth(@Param("from") LocalDateTime from);
}
