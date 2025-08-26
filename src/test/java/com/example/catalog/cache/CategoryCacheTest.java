package com.example.catalog.cache;

import com.example.catalog.domain.Category;
import com.example.catalog.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CategoryCacheTest {
    @Test
    void firstFetchHitsDb_thenServedFromCache() {
        CategoryRepository repo = Mockito.mock(CategoryRepository.class);
        CategoryCache cache = new CategoryCache(repo);

        Category c = new Category("Electronics");
        c.setId(1L);

        when(repo.findById(1L)).thenReturn(Optional.of(c));

        // 1st call -> DB
        assertThat(cache.get(1L)).contains(c);
        verify(repo, times(1)).findById(1L);

        // 2nd call -> cache (no DB call)
        assertThat(cache.get(1L)).contains(c);
        verifyNoMoreInteractions(repo);
    }
}
