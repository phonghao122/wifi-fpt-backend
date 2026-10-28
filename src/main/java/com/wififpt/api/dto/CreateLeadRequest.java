
package com.wififpt.api.dto;

import jakarta.validation.constraints.*;

public record CreateLeadRequest(

        @NotBlank(message = "Vui lòng nhập họ tên")
        @Size(max = 100)
        String fullName,

        @NotBlank(message = "Vui lòng nhập số điện thoại")
        @Pattern(
                regexp = "^0\\d{9}$",
                message = "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0"
        )
        String phone,

        @NotBlank(message = "Vui lòng chọn tỉnh thành")
        String province,

        @NotBlank(message = "Vui lòng chọn gói cước")
        String packageId,

        @Size(max = 200)
        String address,

        @NotNull(message = "Vui lòng xác nhận đồng ý")
        @AssertTrue(message = "Bạn cần đồng ý để gửi đăng ký")
        Boolean consent
) {}
