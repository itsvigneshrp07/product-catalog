package com.example.catalog.web;

import com.example.catalog.dto.CreateProductRequest;
import com.example.catalog.dto.ProductDto;
import com.example.catalog.dto.UpdateProductPriceRequest;
import com.example.catalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) { this.productService = productService; }

    @PostMapping("/categories/{categoryId}/products")
    public ResponseEntity<ProductDto> create(@PathVariable Long categoryId,
                                             @Valid @RequestBody CreateProductRequest req) {
        ProductDto dto = productService.create(categoryId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/categories/{categoryId}/products")
    public List<ProductDto> byCategory(@PathVariable Long categoryId) {
        return productService.findByCategory(categoryId);
    }

    @GetMapping("/products/{id}")
    public ProductDto byId(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PatchMapping("/products/{id}/price")
    public ProductDto updatePrice(@PathVariable Long id, @Valid @RequestBody UpdateProductPriceRequest req) {
        return productService.updatePrice(id, req.price());
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
