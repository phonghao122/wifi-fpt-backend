package com.wififpt.api.repository;

import com.wififpt.api.entity.WebsiteContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebsiteContentRepository
        extends JpaRepository<WebsiteContent, Long> {

    Optional<WebsiteContent> findBySectionKey(String sectionKey);
}