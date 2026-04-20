package com.tlu.khoacntt.config;

import com.tlu.khoacntt.entity.Category;
import com.tlu.khoacntt.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    
    @Override
    public void run(String... args) {
        List<String> defaultCategories = List.of(
                "Tin tức",
                "Thông báo",
                "Tuyển sinh",
                "Hoạt động khoa",
                "Nghiên cứu khoa học"
        );

        for (String name : defaultCategories) {
            if (categoryRepository.findByName(name).isEmpty()) {
                Category category = new Category();
                category.setName(name);
                category.setType(name);
                categoryRepository.save(category);
            }
        }
    }
}
