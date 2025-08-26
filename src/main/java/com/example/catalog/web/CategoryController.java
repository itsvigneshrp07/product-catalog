package com.example.catalog.web;

import com.example.catalog.dto.CategoryDto;
import com.example.catalog.dto.CreateCategoryRequest;
import com.example.catalog.dto.DtoMapper;
import com.example.catalog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) { this.categoryService = categoryService; }

    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CreateCategoryRequest req) {
        CategoryDto dto = categoryService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public CategoryDto get(@PathVariable Long id) {
        return DtoMapper.toDto(categoryService.getEntity(id));
    }

    @PutMapping("/{id}")
    public CategoryDto rename(@PathVariable Long id, @RequestBody CreateCategoryRequest req) {
        return categoryService.updateName(id, req.name());
    }
}
