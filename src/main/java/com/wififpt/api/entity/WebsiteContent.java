package com.wififpt.api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "website_content")
public class WebsiteContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "section_key", nullable = false, unique = true)
    private String sectionKey;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content", nullable = false, columnDefinition = "jsonb")
    private Map<String, Object> content;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(String sectionKey) {
        this.sectionKey = sectionKey;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}