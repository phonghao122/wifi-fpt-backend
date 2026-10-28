
package com.wififpt.api.service;

import com.wififpt.api.dto.PackageRequest;
import com.wififpt.api.entity.InternetPackage;
import com.wififpt.api.repository.InternetPackageRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InternetPackageService {

    private final InternetPackageRepository repository;

    public InternetPackageService(
            InternetPackageRepository repository
    ) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<InternetPackage> getPublicPackages() {
        return repository.findByActiveTrueOrderByDisplayOrderAsc();
    }

    @Transactional(readOnly = true)
    public List<InternetPackage> getAdminPackages() {
        return repository.findAllByOrderByDisplayOrderAsc();
    }

    @Transactional
    public InternetPackage create(PackageRequest request) {
        if (request.code() == null || request.code().isBlank()) {
            throw badRequest("Mã gói cước không được để trống");
        }

        String code = request.code().trim();

        if (repository.existsByCode(code)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Mã gói cước đã tồn tại"
            );
        }

        InternetPackage item = new InternetPackage();
        item.setCode(code);
        applyChanges(item, request);

        return repository.save(item);
    }

    @Transactional
    public InternetPackage update(
            Long id,
            PackageRequest request
    ) {
        InternetPackage item = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Không tìm thấy gói cước"
                ));

        // Không đổi code khi chỉnh sửa để giữ định danh ổn định.
        applyChanges(item, request);

        return repository.save(item);
    }

    @Transactional
    public void hide(Long id) {
        InternetPackage item = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Không tìm thấy gói cước"
                ));

        item.setActive(false);
        repository.save(item);
    }

    private void applyChanges(
            InternetPackage item,
            PackageRequest request
    ) {
        if (request.name() == null || request.name().isBlank()) {
            throw badRequest("Tên gói cước không được để trống");
        }

        if (request.price() == null || request.price() < 0) {
            throw badRequest("Giá gói cước không hợp lệ");
        }

        if (request.features() == null) {
            throw badRequest("Danh sách ưu đãi không được để trống");
        }

        if (request.features().stream().anyMatch(
                feature -> feature == null || feature.isBlank()
        )) {
            throw badRequest("Nội dung ưu đãi không hợp lệ");
        }

        item.setName(request.name().trim());
        item.setDescription(request.description());
        item.setPrice(request.price());
        item.setSpeed(request.speed());
        item.setFeatures(request.features());
        item.setFeatured(Boolean.TRUE.equals(request.featured()));
        item.setActive(request.active() == null
                ? true : request.active());
        item.setDisplayOrder(request.displayOrder() == null
                ? 0 : request.displayOrder());
    }

    private ResponseStatusException badRequest(String message) {
        return new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                message
        );
    }
}
