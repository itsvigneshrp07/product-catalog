package com.example.catalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record UpdateProductPriceRequest(@NotNull @Positive BigDecimal price) {}
