package com.fredodev.riee.shared.status;

import com.fredodev.riee.shared.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/status")
public class ApiStatusController {

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, String>>> getStatus() {
        Map<String, String> data = Map.of(
                "service", "riee",
                "status", "UP"
        );

        return ResponseEntity.ok(ApiResponse.ok(200, "API activa", data));
    }
}
