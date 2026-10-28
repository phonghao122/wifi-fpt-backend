
package com.wififpt.api.controller;

import com.wififpt.api.service.ImageUploadService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/upload")
public class AdminUploadController {

    private final ImageUploadService imageUploadService;

    public AdminUploadController(
            ImageUploadService imageUploadService
    ) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Map<String, String> uploadImage(
            @RequestParam("file") MultipartFile file
    ) {
        return imageUploadService.upload(file);
    }
}
