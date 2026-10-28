
package com.wififpt.api.dto;

import java.util.List;

public record PackageRequest(
        String code,
        String name,
        String description,
        Integer price,
        String speed,
        List<String> features,
        Boolean featured,
        Boolean active,
        Integer displayOrder
) {
}
