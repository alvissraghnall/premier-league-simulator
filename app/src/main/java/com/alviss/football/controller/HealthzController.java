package com.alviss.football.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class HealthzController {

    @GetMapping("/healthz")
    public ResponseEntity<Map<String, String>> healthz() {
        Map<String, String> response = new HashMap<>();
        response.put("URL", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());
        response.put("STATUS", "UP");
        response.put("DETAILS", "Web server is healthy!");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
