package com.example.AnveloShop.api;

import com.example.AnveloShop.domain.Category;
import com.example.AnveloShop.dto.CategoryDto;
import com.example.AnveloShop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category = categoryRepository.save(category);
        categoryDto.setId(category.getId());
        return categoryDto;
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(category -> {
            CategoryDto dto = new CategoryDto();
            dto.setId(category.getId());
            dto.setName(category.getName());
            return dto;
        }).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(categoryDto.getName());
        category = categoryRepository.save(category);
        categoryDto.setId(category.getId());
        return categoryDto;
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}

