
package com.wififpt.api.dto;

public record LeadResponse(
        boolean saved,
        Long leadId,
        String message
) {}
