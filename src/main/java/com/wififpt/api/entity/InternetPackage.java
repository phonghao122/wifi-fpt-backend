
package com.wififpt.api.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "internet_packages")
public class InternetPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 100)
    private String speed;

    @Column(nullable = false, columnDefinition = "jsonb")
    @org.hibernate.annotations.JdbcTypeCode(
            org.hibernate.type.SqlTypes.JSON
    )
    private List<String> features = new ArrayList<>();

    @Column(nullable = false)
    private Boolean featured = false;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void updateTimestamp() {
        updatedAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getSpeed() { return speed; }
    public void setSpeed(String speed) { this.speed = speed; }

    public List<String> getFeatures() { return features; }
    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public Boolean getFeatured() { return featured; }
    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
