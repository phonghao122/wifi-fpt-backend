
package com.wififpt.api.service;

import com.cloudinary.Cloudinary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service
public class ImageUploadService {

    private static final long MAX_SIZE = 5L * 1024 * 1024;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private final Cloudinary cloudinary;

    public ImageUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, String> upload(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Vui lòng chọn ảnh"
            );
        }

        if (file.getSize() > MAX_SIZE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ảnh không được vượt quá 5 MB"
            );
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Chỉ hỗ trợ JPG, PNG và WebP"
            );
        }

        try {
            byte[] bytes = file.getBytes();

            // Kiểm tra chữ ký định dạng, không chỉ tin vào Content-Type
            boolean valid = isSupportedImage(bytes);

            if (!valid ||
                    ImageIO.read(new ByteArrayInputStream(bytes)) == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "File không phải ảnh hợp lệ"
                );
            }

            Map<?, ?> result = cloudinary.uploader().upload(
                    bytes,
                    Map.of(
                            "folder", "wifi-fpt/website",
                            "resource_type", "image",
                            "use_filename", false,
                            "unique_filename", true,
                            "overwrite", false
                    )
            );

            String url = String.valueOf(result.get("secure_url"));
            String publicId = String.valueOf(result.get("public_id"));

            if (!url.startsWith("https://res.cloudinary.com/")) {
                throw new IOException("Cloudinary không trả về URL hợp lệ");
            }

            return Map.of(
                    "url", url,
                    "publicId", publicId
            );

        } catch (ResponseStatusException exception) {
            throw exception;
        } catch (IOException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Không thể tải ảnh lên Cloudinary",
                    exception
            );
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Upload ảnh thất bại",
                    exception
            );
        }
    }

    private boolean isSupportedImage(byte[] bytes) {
        if (bytes.length < 12) return false;

        boolean jpeg =
                (bytes[0] & 0xff) == 0xff &&
                        (bytes[1] & 0xff) == 0xd8 &&
                        (bytes[2] & 0xff) == 0xff;

        boolean png =
                (bytes[0] & 0xff) == 0x89 &&
                        bytes[1] == 'P' &&
                        bytes[2] == 'N' &&
                        bytes[3] == 'G' &&
                        bytes[4] == 0x0d &&
                        bytes[5] == 0x0a &&
                        bytes[6] == 0x1a &&
                        bytes[7] == 0x0a;

        boolean webp =
                bytes[0] == 'R' &&
                        bytes[1] == 'I' &&
                        bytes[2] == 'F' &&
                        bytes[3] == 'F' &&
                        bytes[8] == 'W' &&
                        bytes[9] == 'E' &&
                        bytes[10] == 'B' &&
                        bytes[11] == 'P';

        return jpeg || png || webp;
    }
}
