package com.example.reverse.proxy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<byte[]> healthCheck(){
        return ResponseEntity.status(HttpStatus.FOUND).body("UP".getBytes());
    }
}
