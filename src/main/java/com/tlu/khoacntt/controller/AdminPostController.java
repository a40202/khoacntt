package com.tlu.khoacntt.controller;

import com.tlu.khoacntt.dto.request.PostRequest;
import com.tlu.khoacntt.dto.response.PostImageResponse;
import com.tlu.khoacntt.dto.response.PostResponse;
import com.tlu.khoacntt.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminPostController {

    private final PostService postService;

    // Lấy tất cả bài viết (kể cả chưa xuất bản)
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // Tạo bài viết mới
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request) {
        PostResponse response = postService.createPost(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Upload ảnh cho bài viết, lưu file vào uploads/posts/ và metadata vào post_images
    @PostMapping("/{postId}/images")
    public ResponseEntity<PostImageResponse> uploadPostImage(
            @PathVariable Integer postId,
            @RequestParam("file") MultipartFile file
    ) {
        return new ResponseEntity<>(postService.uploadPostImage(postId, file), HttpStatus.CREATED);
    }
    // Lấy chi tiết 1 bài viết theo ID (Admin dùng để load form sửa)
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Cập nhật bài viết — adminId không bắt buộc khi update (giữ nguyên người đăng ban đầu)
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Integer id,
            @Valid @RequestBody PostRequest request
    ) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    // Xóa bài viết
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // Lấy danh sách bài viết theo danh mục
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostResponse>> getPostsByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }

}