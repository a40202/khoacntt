package com.tlu.khoacntt.controller;

import com.tlu.khoacntt.dto.response.PostResponse;
import com.tlu.khoacntt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostPublicController {

    private final PostService postService;

    // GET /api/public/posts — Tất cả bài viết đã published
    @GetMapping
    public ResponseEntity<List<PostResponse>> getNewsFeed() {
        return ResponseEntity.ok(postService.getPublishedPosts());
    }

    // GET /api/public/posts/{slug} — Chi tiết bài viết theo slug
    @GetMapping("/{slug}")
    public ResponseEntity<PostResponse> getPostDetail(@PathVariable String slug) {
        return ResponseEntity.ok(postService.getPostBySlug(slug));
    }

    // GET /api/public/posts/category/{categoryId} — Lọc theo danh mục
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostResponse>> getPostsByCat(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }

    // GET /api/public/posts/search?title=... — Tìm kiếm bài viết
    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPosts(@RequestParam String title) {
        return ResponseEntity.ok(postService.searchPostsByTitle(title));
    }

    // GET /api/public/posts/suggestions?query=... — Gợi ý tìm kiếm
    @GetMapping("/suggestions")
    public ResponseEntity<List<PostResponse>> getSuggestions(@RequestParam("query") String query) {
        if (query.length() < 2) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(postService.getSearchSuggestions(query));
    }

    // POST /api/public/posts/{id}/view — Tăng lượt xem +1
    @PostMapping("/{id}/view")
    public ResponseEntity<Map<String, String>> incrementView(@PathVariable Integer id) {
        postService.incrementView(id);
        return ResponseEntity.ok(Map.of("message", "View counted"));
    }
}
