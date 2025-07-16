package com.devops_jp_vc.championshipapi.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> get(HttpServletRequest request) {
        String baseUrl = String.format("%s://%s:%d",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort());

        String swaggerUrl = baseUrl + "/swagger-ui.html";

        Map<String, String> response = Map.of(
                "message", "Bem-vindo à Championship API!",
                "swaggerUrl", swaggerUrl);

        return ResponseEntity.ok(response);
    }
}
