
package com.wififpt.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs
    ) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);

        if (keyBytes.length < 32) {
            throw new IllegalArgumentException(
                    "JWT secret phải có ít nhất 256 bit"
            );
        }

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = expirationMs;
    }

    // Tạo JWT sau khi đăng nhập thành công
    public String generateToken(String username) {
        Date now = new Date();

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(
                        new Date(now.getTime() + expirationMs)
                )
                .signWith(secretKey)
                .compact();
    }

    // Đọc username từ JWT và kiểm tra chữ ký, hạn dùng
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
