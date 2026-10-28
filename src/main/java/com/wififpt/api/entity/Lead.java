
package com.wififpt.api.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "leads")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "full_name",
            nullable = false,
            length = 100
    )
    private String fullName;

    @Column(
            name = "phone",
            nullable = false,
            length = 20
    )
    private String phone;

    @Column(
            name = "province",
            nullable = false,
            length = 100
    )
    private String province;

    @Column(
            name = "package_id",
            nullable = false,
            length = 50
    )
    private String packageId;

    @Column(
            name = "address",
            length = 200
    )
    private String address;

    @Column(
            name = "source",
            nullable = false,
            length = 50
    )
    private String source = "website";

    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private String status = "NEW";

    @Column(
            name = "consent",
            nullable = false
    )
    private Boolean consent;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private OffsetDateTime createdAt;

    @Column(
            name = "admin_note",
            columnDefinition = "TEXT"
    )
    private String adminNote;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private OffsetDateTime updatedAt;

    @Column(name = "contacted_at")
    private OffsetDateTime contactedAt;

    // ===== JPA LIFECYCLE =====

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();

        if (createdAt == null) {
            createdAt = now;
        }

        if (updatedAt == null) {
            updatedAt = now;
        }

        if (source == null) {
            source = "website";
        }

        if (status == null) {
            status = "NEW";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getConsent() {
        return consent;
    }

    public void setConsent(Boolean consent) {
        this.consent = consent;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public OffsetDateTime getContactedAt() {
        return contactedAt;
    }

    public void setContactedAt(
            OffsetDateTime contactedAt
    ) {
        this.contactedAt = contactedAt;
    }
}
