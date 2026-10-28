
package com.wififpt.api.controller;

import com.wififpt.api.entity.InternetPackage;
import com.wififpt.api.service.InternetPackageService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PublicPackageController {

    private final InternetPackageService service;

    public PublicPackageController(
            InternetPackageService service
    ) {
        this.service = service;
    }

    @GetMapping
    public List<InternetPackage> getPackages() {
        return service.getPublicPackages();
    }
}
