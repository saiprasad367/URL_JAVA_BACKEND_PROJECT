package com.shortify.controller;

import com.shortify.model.UrlRecord;
import com.shortify.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Value("${base.domain}")
    private String baseDomain;

    public static class ShortenRequest {
        public String longUrl;
        public String customAlias;
        public String expiryDate;
    }

    @PostMapping("/api/v1/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody ShortenRequest request) {
        try {
            LocalDateTime expiryDate = null;
            if (request.expiryDate != null && !request.expiryDate.isEmpty()) {
                // Assuming standard ISO 8601 string
                if (request.expiryDate.endsWith("Z")) {
                    request.expiryDate = request.expiryDate.substring(0, request.expiryDate.length() - 1);
                }
                expiryDate = LocalDateTime.parse(request.expiryDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }

            UrlRecord url = urlService.shortenUrl(request.longUrl, request.customAlias, expiryDate);

            Map<String, Object> response = new HashMap<>();
            response.put("shortUrl", baseDomain + "/" + url.getShortCode());
            response.put("shortCode", url.getShortCode());
            response.put("createdAt", url.getCreatedAt().toString());
            response.put("expiryDate", url.getExpiryAt() != null ? url.getExpiryAt().toString() : null);
            response.put("clickCount", String.valueOf(url.getClickCount()));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/api/v1/analytics/{shortCode}")
    public ResponseEntity<?> getAnalytics(@PathVariable String shortCode) {
        try {
            UrlRecord url = urlService.getAnalytics(shortCode);
            Map<String, Object> response = new HashMap<>();
            response.put("shortCode", url.getShortCode());
            response.put("longUrl", url.getLongUrl());
            response.put("createdAt", url.getCreatedAt().toString());
            response.put("expiryDate", url.getExpiryAt() != null ? url.getExpiryAt().toString() : null);
            response.put("clickCount", String.valueOf(url.getClickCount()));
            response.put("lastAccessedAt", url.getLastAccessedAt() != null ? url.getLastAccessedAt().toString() : null);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/api/v1/{shortCode}")
    public ResponseEntity<?> deleteUrl(@PathVariable String shortCode) {
        try {
            urlService.deleteUrl(shortCode);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Success");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/api/v1/urls")
    public ResponseEntity<List<Map<String, Object>>> listUrls() {
        List<UrlRecord> urls = urlService.getAllUrls();
        
        // Sorting in memory to match previous behavior: order by created_at desc
        urls.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));

        List<Map<String, Object>> response = urls.stream().map(url -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", url.getId());
            map.put("short_code", url.getShortCode());
            map.put("long_url", url.getLongUrl());
            map.put("click_count", String.valueOf(url.getClickCount()));
            map.put("created_at", url.getCreatedAt().toString());
            map.put("expiry_at", url.getExpiryAt() != null ? url.getExpiryAt().toString() : null);
            map.put("last_accessed_at", url.getLastAccessedAt() != null ? url.getLastAccessedAt().toString() : null);
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortCode}")
    public void resolveUrl(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        try {
            String longUrl = urlService.resolveUrl(shortCode);
            response.sendRedirect(longUrl);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "URL not found");
        } catch (IllegalStateException e) {
            response.sendError(HttpStatus.GONE.value(), "URL has expired");
        }
    }
}
