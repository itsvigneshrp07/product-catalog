package com.example.catalog.service;

import com.example.catalog.domain.Category;
import com.example.catalog.domain.Product;
import com.example.catalog.dto.CreateProductRequest;
import com.example.catalog.dto.DtoMapper;
import com.example.catalog.dto.ProductDto;
import com.example.catalog.repository.ProductRepository;
import com.example.catalog.web.error.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Transactional
    public ProductDto create(Long categoryId, CreateProductRequest req) {
        Category category = categoryService.getEntity(categoryId);
        Product saved = productRepository.save(new Product(req.name(), req.price(), category));
        return DtoMapper.toDto(saved);
    }

    public List<ProductDto> findByCategory(Long categoryId) {
        categoryService.getEntity(categoryId);
        return productRepository.findByCategory_Id(categoryId).stream().map(DtoMapper::toDto).toList();
    }

    public ProductDto findById(Long id) {
        Product p = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found: " + id));
        return DtoMapper.toDto(p);
    }

    @Transactional
    public ProductDto updatePrice(Long id, java.math.BigDecimal newPrice) {
        Product p = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found: " + id));
        p.setPrice(newPrice);
        return DtoMapper.toDto(productRepository.save(p));
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }
}
