
package com.wififpt.api.dto;

public record LeadStatsResponse(
        long total,
        long newLeads,
        long contacted,
        long converted,
        long cancelled,
        double conversionRate
) {
}
