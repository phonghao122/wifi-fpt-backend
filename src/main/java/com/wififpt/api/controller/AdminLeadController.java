
package com.wififpt.api.controller;

import com.wififpt.api.dto.LeadStatsResponse;
import com.wififpt.api.dto.UpdateLeadRequest;
import com.wififpt.api.entity.Lead;
import com.wififpt.api.service.LeadService;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/leads")
public class AdminLeadController {

    private final LeadService leadService;

    public AdminLeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    // 1. DANH SÁCH + TÌM KIẾM + LỌC + PHÂN TRANG

    @GetMapping
    public Page<Lead> getLeads(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return leadService.getLeads(
                keyword,
                status,
                page,
                size
        );
    }

    // 2. THỐNG KÊ CRM

    @GetMapping("/stats")
    public LeadStatsResponse getStats() {
        return leadService.getLeadStats();
    }

    // 3. XEM CHI TIẾT KHÁCH HÀNG

    @GetMapping("/{id}")
    public Lead getLeadById(
            @PathVariable Long id
    ) {
        return leadService.getLeadById(id);
    }

    // 4. CẬP NHẬT TRẠNG THÁI VÀ GHI CHÚ

    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Lead updateLead(
            @PathVariable Long id,
            @RequestBody UpdateLeadRequest request
    ) {
        return leadService.updateLead(id, request);
    }
}
