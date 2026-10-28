
package com.wififpt.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        return Map.of(
                "message", "WiFi FPT API running",
                "version", "4.1.2"
        );
    }
}
