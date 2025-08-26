package com.example.catalog.dto;

import com.example.catalog.domain.Category;
import com.example.catalog.domain.Product;

public final class DtoMapper {
    private DtoMapper() {}

    public static CategoryDto toDto(Category c) {
        return new CategoryDto(c.getId(), c.getName());
    }

    public static ProductDto toDto(Product p) {
        return new ProductDto(p.getId(), p.getName(), p.getPrice(), p.getCategory().getId());
    }
}
