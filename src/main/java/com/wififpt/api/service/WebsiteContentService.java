package com.wififpt.api.service;

import com.wififpt.api.entity.WebsiteContent;
import com.wififpt.api.repository.WebsiteContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class WebsiteContentService {

    private final WebsiteContentRepository repository;

    public WebsiteContentService(WebsiteContentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getContent(String sectionKey) {
        return repository.findBySectionKey(sectionKey)
                .map(WebsiteContent::getContent)
                .orElse(Map.of());
    }

    @Transactional
    public Map<String, Object> updateContent(
            String sectionKey,
            Map<String, Object> content
    ) {
        WebsiteContent entity = repository
                .findBySectionKey(sectionKey)
                .orElseGet(WebsiteContent::new);

        entity.setSectionKey(sectionKey);
        entity.setContent(content);

        return repository.save(entity).getContent();
    }
}