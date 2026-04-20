package com.tlu.khoacntt.mapper;

import com.tlu.khoacntt.dto.response.PostResponse;
import com.tlu.khoacntt.dto.response.PostImageResponse;
import com.tlu.khoacntt.entity.Post;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PostMapper {

    public PostResponse toResponse(Post post) {
        if (post == null) {
            return null;
        }

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .slug(post.getSlug())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .status(post.getStatus())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                // Ánh xạ Category Info
                .category(post.getCategory() != null ? 
                    new PostResponse.CategoryInfo(
                        post.getCategory().getId(), 
                        post.getCategory().getName()
                    ) : null)
                // Ánh xạ Author Info
                .author(post.getAdmin() != null ? 
                    new PostResponse.AuthorInfo(
                        post.getAdmin().getId(), 
                        post.getAdmin().getFullName()
                    ) : null)
                .images(post.getImages() == null
                        ? Collections.emptyList()
                        : post.getImages().stream()
                        .map(img -> PostImageResponse.builder()
                                .imageId(img.getImageId())
                                .imageUrl(img.getImageUrl())
                                .createdAt(img.getCreatedAt())
                                .postId(post.getId())
                                .build())
                        .toList())
                .build();
    }
}