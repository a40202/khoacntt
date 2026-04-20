package com.tlu.khoacntt.repository;

import com.tlu.khoacntt.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Integer> {

    List<PostImage> findByPost_IdOrderByCreatedAtDesc(Integer postId);
}
