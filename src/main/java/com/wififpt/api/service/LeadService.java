
package com.wififpt.api.service;

import com.wififpt.api.dto.CreateLeadRequest;
import com.wififpt.api.dto.LeadResponse;
import com.wififpt.api.dto.UpdateLeadRequest;
import com.wififpt.api.dto.LeadStatsResponse;

import com.wififpt.api.entity.Lead;
import com.wififpt.api.repository.LeadRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Set;

@Service
public class LeadService {

    private static final Set<String> VALID_STATUSES =
            Set.of(
                    "NEW",
                    "CONTACTED",
                    "CONVERTED",
                    "CANCELLED"
            );

    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    // 1. TIẾP NHẬN ĐĂNG KÝ WIFI - GIỮ NGUYÊN

    @Transactional
    public LeadResponse createLead(CreateLeadRequest request) {

        Lead lead = new Lead();

        lead.setFullName(request.fullName().trim());
        lead.setPhone(request.phone().trim());
        lead.setProvince(request.province());
        lead.setPackageId(request.packageId());
        lead.setAddress(request.address());
        lead.setConsent(request.consent());

        Lead savedLead = leadRepository.save(lead);

        return new LeadResponse(
                true,
                savedLead.getId(),
                "Đăng ký đã được tiếp nhận"
        );
    }

    // 2. DANH SÁCH + TÌM KIẾM + LỌC + PHÂN TRANG

    @Transactional(readOnly = true)
    public Page<Lead> getLeads(
            String keyword,
            String status,
            int page,
            int size
    ) {

        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tham số phân trang không hợp lệ"
            );
        }

        String searchKeyword =
                keyword == null ? "" : keyword.trim();

        String searchStatus =
                status == null ? "" : status.trim().toUpperCase();

        if (!searchStatus.isEmpty()
                && !VALID_STATUSES.contains(searchStatus)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Trạng thái không hợp lệ"
            );
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Direction.DESC,
                        "createdAt"
                )
        );

        boolean hasKeyword = !searchKeyword.isEmpty();
        boolean hasStatus = !searchStatus.isEmpty();

        if (hasKeyword && hasStatus) {
            return leadRepository.searchByStatus(
                    searchKeyword,
                    searchStatus,
                    pageable
            );
        }

        if (hasKeyword) {
            return leadRepository.searchLeads(
                    searchKeyword,
                    pageable
            );
        }

        if (hasStatus) {
            return leadRepository.findByStatus(
                    searchStatus,
                    pageable
            );
        }

        return leadRepository.findAll(pageable);
    }

    // 3. XEM CHI TIẾT KHÁCH HÀNG

    @Transactional(readOnly = true)
    public Lead getLeadById(Long id) {

        return leadRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Không tìm thấy khách hàng"
                        )
                );
    }

    // 4. CẬP NHẬT TRẠNG THÁI VÀ GHI CHÚ

    @Transactional
    public Lead updateLead(
            Long id,
            UpdateLeadRequest request
    ) {

        Lead lead = getLeadById(id);

        if (request.status() == null
                || request.status().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Trạng thái không được để trống"
            );
        }

        String newStatus =
                request.status().trim().toUpperCase();

        if (!VALID_STATUSES.contains(newStatus)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Trạng thái không hợp lệ"
            );
        }

        // Ghi nhận lần liên hệ đầu tiên
        if ("CONTACTED".equals(newStatus)
                && lead.getContactedAt() == null) {

            lead.setContactedAt(
                    OffsetDateTime.now()
            );
        }

        lead.setStatus(newStatus);

        // null nghĩa là giữ nguyên ghi chú hiện tại
        if (request.adminNote() != null) {
            lead.setAdminNote(
                    request.adminNote().trim()
            );
        }

        return leadRepository.save(lead);
    }

    // 5. THỐNG KÊ CRM

    @Transactional(readOnly = true)
    public LeadStatsResponse getLeadStats() {

        long total = leadRepository.count();

        long newLeads =
                leadRepository.countByStatus("NEW");

        long contacted =
                leadRepository.countByStatus("CONTACTED");

        long converted =
                leadRepository.countByStatus("CONVERTED");

        long cancelled =
                leadRepository.countByStatus("CANCELLED");

        double conversionRate = total == 0
                ? 0.0
                : Math.round(
                converted * 10000.0 / total
        ) / 100.0;

        return new LeadStatsResponse(
                total,
                newLeads,
                contacted,
                converted,
                cancelled,
                conversionRate
        );
    }
}
