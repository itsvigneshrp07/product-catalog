package com.example.catalog.cache;

import com.example.catalog.domain.Category;
import com.example.catalog.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class CategoryCache {
    private final CategoryRepository categoryRepository;
    private final ConcurrentMap<Long, Category> byId = new ConcurrentHashMap<>();

    public CategoryCache(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> get(Long id) {
        Category cached = byId.computeIfAbsent(id, missingId ->
                categoryRepository.findById(missingId).orElse(null)
        );
        return Optional.ofNullable(cached);
    }

    public void put(Category category) {
        if (category != null && category.getId() != null) {
            byId.put(category.getId(), category);
        }
    }

    public void evict(Long id) {
        byId.remove(id);
    }

    public void clear() {
        byId.clear();
    }
}
