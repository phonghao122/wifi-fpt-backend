package com.wififpt.api.controller;

import com.wififpt.api.service.WebsiteContentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/content")
public class PublicContentController {

    private final WebsiteContentService service;

    public PublicContentController(WebsiteContentService service) {
        this.service = service;
    }

    @GetMapping("/{sectionKey}")
    public Map<String, Object> getContent(
            @PathVariable String sectionKey
    ) {
        return service.getContent(sectionKey);
    }
}