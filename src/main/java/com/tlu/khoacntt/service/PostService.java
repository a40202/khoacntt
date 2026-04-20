package com.tlu.khoacntt.service;

import com.tlu.khoacntt.dto.request.PostRequest;
import com.tlu.khoacntt.dto.response.PostImageResponse;
import com.tlu.khoacntt.dto.response.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    List<PostResponse> getAllPosts();
    List<PostResponse> getPublishedPosts();
    PostResponse getPostBySlug(String slug);
    List<PostResponse> getPostsByCategory(Integer categoryId);
    List<PostResponse> searchPostsByTitle(String title);
    PostResponse createPost(PostRequest request);
    PostImageResponse uploadPostImage(Integer postId, MultipartFile file);
    void deletePost(Integer id);
    List<PostResponse> getSearchSuggestions(String query);
}