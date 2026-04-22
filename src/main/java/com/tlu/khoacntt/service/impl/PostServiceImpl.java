package com.tlu.khoacntt.service.impl;

import com.tlu.khoacntt.dto.request.PostRequest;
import com.tlu.khoacntt.dto.response.PostImageResponse;
import com.tlu.khoacntt.dto.response.PostResponse;
import com.tlu.khoacntt.entity.Admin;
import com.tlu.khoacntt.entity.Category;
import com.tlu.khoacntt.entity.Post;
import com.tlu.khoacntt.entity.PostImage;
import com.tlu.khoacntt.exception.ResourceNotFoundException;
import com.tlu.khoacntt.mapper.PostMapper;
import com.tlu.khoacntt.repository.AdminRepository;
import com.tlu.khoacntt.repository.CategoryRepository;
import com.tlu.khoacntt.repository.PostImageRepository;
import com.tlu.khoacntt.repository.PostRepository;
import com.tlu.khoacntt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final PostImageRepository postImageRepository;
    private final PostMapper postMapper; // Inject mapper vào đây

    @Override
    public List<PostResponse> getSearchSuggestions(String query) {
        // Tìm kiếm và giới hạn trả về khoảng 5-7 kết quả để hiện gợi ý cho mượt
        return postRepository.findByTitleContainingIgnoreCase(query)
                .stream()
                .limit(7) 
                .map(this::mapToSimpleResponse) // Dùng hàm map gọn nhẹ
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> searchPostsByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return postRepository.findByStatusAndTitleContainingIgnoreCase("published", title.trim())
                .stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
    }

    private PostResponse mapToSimpleResponse(Post post) {
        PostResponse res = new PostResponse();
        res.setId(post.getId());
        res.setTitle(post.getTitle());
        res.setSlug(post.getSlug());
        return res;
    }


    
    // 1. Lấy danh sách bài viết đã xuất bản (cho News Feed)
    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPublishedPosts() {
        return postRepository.findByStatus("published").stream()
                .map(postMapper::toResponse)
                .toList();
    }

    // 2. Lấy chi tiết bài viết qua Slug và tăng lượt xem
    @Override
    @Transactional
    public PostResponse getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Bài viết không tồn tại với slug: " + slug));
        
        // Tự động tăng lượt xem
        postRepository.incrementViewCount(post.getId());
        
        return postMapper.toResponse(post);
    }

    // 3. Lấy bài viết theo danh mục (Category)
    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByCategory(Integer categoryId) {
        return postRepository.findByCategory_Id(categoryId).stream()
                .map(postMapper::toResponse)
                .toList();
    }

    // 4. Lấy tất cả bài viết (Admin dùng)
    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toResponse)
                .toList();
    }

    // 5. Tạo bài viết mới
    @Override
    @Transactional
    public PostResponse createPost(PostRequest request) {
        if (request.getAdminId() == null || request.getAdminId().isBlank()) {
            throw new IllegalArgumentException("ID người đăng không được để trống");
        }
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy danh mục với ID: " + request.getCategoryId()));
        Admin admin = adminRepository.findById(request.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người đăng với ID: " + request.getAdminId()));

        String slug = resolveSlug(request);

        Post post = Post.builder()
                .title(request.getTitle())
                .slug(slug)
                .content(request.getContent())
                .thumbnail(request.getThumbnail())
                .status(request.getStatus() != null ? request.getStatus() : "draft")
                .viewCount(0)
                .category(category)
                .admin(admin)
                .build();

        Post savedPost = postRepository.save(post);
        return postMapper.toResponse(savedPost);
    }

    @Override
    @Transactional
    public PostImageResponse uploadPostImage(Integer postId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File ảnh không được để trống");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với ID: " + postId));

        String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename().trim() : "";
        String ext = "";
        int dot = originalName.lastIndexOf('.');
        if (dot >= 0 && dot < originalName.length() - 1) {
            ext = originalName.substring(dot).toLowerCase(Locale.ROOT);
        }
        if (!List.of(".jpg", ".jpeg", ".png", ".gif", ".webp").contains(ext)) {
            throw new IllegalArgumentException("Chỉ hỗ trợ ảnh: jpg, jpeg, png, gif, webp");
        }

        String fileName = postId + "_" + UUID.randomUUID().toString().replace("-", "") + ext;
        Path uploadDir = Paths.get("uploads", "posts");
        Path targetPath = uploadDir.resolve(fileName);
        try {
            Files.createDirectories(uploadDir);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Không thể lưu file ảnh: " + ex.getMessage());
        }

        String imageUrl = "/uploads/posts/" + fileName;
        PostImage saved = postImageRepository.save(PostImage.builder()
                .imageUrl(imageUrl)
                .post(post)
                .build());

        return PostImageResponse.builder()
                .imageId(saved.getImageId())
                .imageUrl(saved.getImageUrl())
                .createdAt(saved.getCreatedAt())
                .postId(postId)
                .build();
    }

    private String resolveSlug(PostRequest request) {
        String raw = request.getSlug();
        if (raw != null && !raw.isBlank()) {
            String trimmed = raw.trim();
            if (postRepository.existsBySlug(trimmed)) {
                throw new IllegalArgumentException("Slug đã tồn tại: " + trimmed);
            }
            return trimmed;
        }
        return ensureUniqueSlug(slugifyTitle(request.getTitle()));
    }

    private String slugifyTitle(String title) {
        if (title == null || title.isBlank()) {
            return "bai-viet";
        }
        String s = title.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
        if (s.length() > 255) {
            s = s.substring(0, 255);
        }
        return s;
    }

    private String ensureUniqueSlug(String base) {
        String slug = base;
        int n = 0;
        while (postRepository.existsBySlug(slug)) {
            slug = base + "-" + (++n);
        }
        return slug;
    }

    // Tăng lượt xem +1 (gọi từ public API)
    @Override
    @Transactional
    public void incrementView(Integer id) {
        if (!postRepository.existsById(id)) {
            throw new com.tlu.khoacntt.exception.ResourceNotFoundException("Không tìm thấy bài viết với ID: " + id);
        }
        postRepository.incrementViewCount(id);
    }

    // 6. Lấy chi tiết bài viết theo ID (Admin dùng)
    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với ID: " + id));
        return postMapper.toResponse(post);
    }

    // 7. Cập nhật bài viết
    @Override
    @Transactional
    public PostResponse updatePost(Integer id, PostRequest request) {
        // Tìm bài viết hiện có
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với ID: " + id));

        // Cập nhật danh mục nếu thay đổi
        if (!post.getCategory().getId().equals(request.getCategoryId())) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy danh mục với ID: " + request.getCategoryId()));
            post.setCategory(category);
        }

        // Xử lý slug: chỉ cập nhật nếu slug mới khác slug cũ
        String newSlug = request.getSlug();
        if (newSlug != null && !newSlug.isBlank()) {
            newSlug = newSlug.trim();
            // Kiểm tra slug mới có trùng với bài viết KHÁC không (không phải chính nó)
            if (!newSlug.equals(post.getSlug()) && postRepository.existsBySlug(newSlug)) {
                throw new IllegalArgumentException("Slug đã tồn tại: " + newSlug);
            }
            post.setSlug(newSlug);
        } else if (!post.getTitle().equals(request.getTitle())) {
            // Nếu không truyền slug nhưng tiêu đề thay đổi → tự sinh slug mới
            String base = slugifyTitle(request.getTitle());
            // Loại trừ slug hiện tại của chính bài viết này khi kiểm tra unique
            String generatedSlug = base;
            int n = 0;
            while (postRepository.existsBySlug(generatedSlug) && !generatedSlug.equals(post.getSlug())) {
                generatedSlug = base + "-" + (++n);
            }
            post.setSlug(generatedSlug);
        }

        // Cập nhật các trường còn lại
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        if (request.getThumbnail() != null) {
            post.setThumbnail(request.getThumbnail());
        }
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            post.setStatus(request.getStatus());
        }

        return postMapper.toResponse(postRepository.save(post));
    }

    // 8. Xóa bài viết
    @Override
    @Transactional
    public void deletePost(Integer id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy bài viết để xóa với ID: " + id);
        }
        postRepository.deleteById(id);
    }
}