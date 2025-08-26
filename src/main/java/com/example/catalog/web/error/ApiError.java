package com.example.catalog.web.error;

import java.time.Instant;
import java.util.Map;

public record ApiError(String error, String message, int status, Instant timestamp, Map<String, String> fieldErrors) {}
