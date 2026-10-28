package com.wififpt.api.controller;

import com.wififpt.api.service.WebsiteContentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/content")
public class AdminContentController {

    private static final Set<String> ALLOWED_SECTIONS =
            Set.of("hero", "benefits", "contact", "promotions");

    private final WebsiteContentService service;

    public AdminContentController(WebsiteContentService service) {
        this.service = service;
    }

    @GetMapping("/{sectionKey}")
    public Map<String, Object> getContent(
            @PathVariable String sectionKey
    ) {
        validateSection(sectionKey);
        return service.getContent(sectionKey);
    }

    @PutMapping("/{sectionKey}")
    public Map<String, Object> updateContent(
            @PathVariable String sectionKey,
            @RequestBody Map<String, Object> content
    ) {
        validateSection(sectionKey);
        return service.updateContent(sectionKey, content);
    }

    private void validateSection(String sectionKey) {
        if (!ALLOWED_SECTIONS.contains(sectionKey)) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Khu vực nội dung không hợp lệ"
            );
        }
    }
}