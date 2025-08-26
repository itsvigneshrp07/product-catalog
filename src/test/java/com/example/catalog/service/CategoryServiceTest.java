package com.example.catalog.service;

import com.example.catalog.cache.CategoryCache;
import com.example.catalog.domain.Category;
import com.example.catalog.repository.CategoryRepository;
import com.example.catalog.web.error.NotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    @Test
    void updateRefreshesCache() {
        CategoryRepository repo = mock(CategoryRepository.class);
        CategoryCache cache = spy(new CategoryCache(repo));
        CategoryService service = new CategoryService(repo, cache);

        Category c = new Category("Electronics");
        c.setId(1L);

        when(repo.findById(1L)).thenReturn(Optional.of(c));
        when(repo.existsByName("Gadgets")).thenReturn(false);
        when(repo.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

        service.updateName(1L, "Gadgets");
        verify(cache, times(1)).put(any(Category.class));
    }

    @Test
    void getUnknownCategoryThrows404() {
        CategoryRepository repo = mock(CategoryRepository.class);
        CategoryCache cache = new CategoryCache(repo);
        CategoryService service = new CategoryService(repo, cache);

        when(repo.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getEntity(999L)).isInstanceOf(NotFoundException.class);
    }
}
