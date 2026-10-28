
package com.wififpt.api;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGeneratorTest {

    @Test
    void generatePasswordHash() {
        String newPassword = "Ph@300401";

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String hashedPassword = encoder.encode(newPassword);

        System.out.println("BCrypt Hash: " + hashedPassword);
        System.out.println(
                "Verify: " + encoder.matches(newPassword, hashedPassword)
        );
    }
}
