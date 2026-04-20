package com.tlu.khoacntt.controller;

import com.tlu.khoacntt.dto.response.PostResponse; // Cập nhật đúng package DTO
import com.tlu.khoacntt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/public/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostPublicController {

    private final PostService postService;
    @GetMapping
    public ResponseEntity<List<PostResponse>> getNewsFeed() {
        List<PostResponse> posts = postService.getPublishedPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<PostResponse> getPostDetail(@PathVariable String slug) {
        PostResponse post = postService.getPostBySlug(slug);
        return ResponseEntity.ok(post);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostResponse>> getPostsByCat(@PathVariable Integer categoryId) {
        List<PostResponse> posts = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPosts(@RequestParam String title) {
        // Tìm kiếm bài viết theo tiêu đề cho trang public
        return ResponseEntity.ok(postService.searchPostsByTitle(title));
    }
    @GetMapping("/suggestions")
    public ResponseEntity<List<PostResponse>> getSuggestions(@RequestParam("query") String query) {
        if (query.length() < 2) { // Chỉ gợi ý khi gõ từ 2 ký tự trở lên
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(postService.getSearchSuggestions(query));
    }
}