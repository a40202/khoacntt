package com.tlu.khoacntt.service.impl;

import com.tlu.khoacntt.dto.request.CategoryRequest;
import com.tlu.khoacntt.dto.response.CategoryResponse;
import com.tlu.khoacntt.entity.Category;
import com.tlu.khoacntt.exception.ResourceNotFoundException;
import com.tlu.khoacntt.repository.CategoryRepository;
import com.tlu.khoacntt.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        String name = request.getName().trim();
        if (categoryRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Danh mục đã tồn tại: " + name);
        }

        Category category = new Category();
        category.setName(name);
        category.setType(resolveType(request));

        return toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với ID: " + id));

        String name = request.getName().trim();
        categoryRepository.findByName(name)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Danh mục đã tồn tại: " + name);
                });

        category.setName(name);
        category.setType(resolveType(request));

        return toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với ID: " + id));
        try {
            categoryRepository.delete(category);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Không thể xóa danh mục đang được sử dụng bởi bài viết");
        }
    }

    private String resolveType(CategoryRequest request) {
        if (request.getType() == null || request.getType().trim().isEmpty()) {
            return request.getName().trim();
        }
        return request.getType().trim();
    }

    private CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .build();
    }
}
