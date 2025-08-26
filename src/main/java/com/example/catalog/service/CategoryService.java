package com.example.catalog.service;

import com.example.catalog.cache.CategoryCache;
import com.example.catalog.domain.Category;
import com.example.catalog.dto.CategoryDto;
import com.example.catalog.dto.CreateCategoryRequest;
import com.example.catalog.dto.DtoMapper;
import com.example.catalog.repository.CategoryRepository;
import com.example.catalog.web.error.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryCache categoryCache;

    public CategoryService(CategoryRepository categoryRepository, CategoryCache categoryCache) {
        this.categoryRepository = categoryRepository;
        this.categoryCache = categoryCache;
    }

    @Transactional
    public CategoryDto create(CreateCategoryRequest req) {
        if (categoryRepository.existsByName(req.name())) {
            throw new IllegalArgumentException("Category name already exists: " + req.name());
        }
        Category saved = categoryRepository.save(new Category(req.name()));
        categoryCache.put(saved);
        return DtoMapper.toDto(saved);
    }

    public Category getEntity(Long id) {
        return categoryCache.get(id).orElseThrow(() -> new NotFoundException("Category not found: " + id));
    }

    @Transactional
    public CategoryDto updateName(Long id, String newName) {
        Category c = getEntity(id);
        if (newName == null) return DtoMapper.toDto(c);
        if (!c.getName().equals(newName) && categoryRepository.existsByName(newName)) {
            throw new IllegalArgumentException("Category name already exists: " + newName);
        }
        c.setName(newName);
        Category saved = categoryRepository.save(c);
        categoryCache.put(saved);
        return DtoMapper.toDto(saved);
    }
}
