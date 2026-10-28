
package com.wififpt.api.controller;

import com.wififpt.api.dto.CreateLeadRequest;
import com.wififpt.api.dto.LeadResponse;
import com.wififpt.api.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeadResponse createLead(
            @Valid @RequestBody CreateLeadRequest request
    ) {
        return leadService.createLead(request);
    }
}
