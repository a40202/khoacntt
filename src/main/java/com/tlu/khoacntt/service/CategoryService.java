package com.tlu.khoacntt.service;

import com.tlu.khoacntt.dto.request.CategoryRequest;
import com.tlu.khoacntt.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(Integer id, CategoryRequest request);
    void deleteCategory(Integer id);
}
