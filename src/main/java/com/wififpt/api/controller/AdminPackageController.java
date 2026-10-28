
package com.wififpt.api.controller;

import com.wififpt.api.dto.PackageRequest;
import com.wififpt.api.entity.InternetPackage;
import com.wififpt.api.service.InternetPackageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/packages")
public class AdminPackageController {

    private final InternetPackageService service;

    public AdminPackageController(
            InternetPackageService service
    ) {
        this.service = service;
    }

    @GetMapping
    public List<InternetPackage> getAll() {
        return service.getAdminPackages();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InternetPackage create(
            @RequestBody PackageRequest request
    ) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public InternetPackage update(
            @PathVariable Long id,
            @RequestBody PackageRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hide(
            @PathVariable Long id
    ) {
        service.hide(id);
        return ResponseEntity.noContent().build();
    }


}
