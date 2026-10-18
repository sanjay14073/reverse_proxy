package com.example.reverse.proxy.controller;

import com.example.reverse.proxy.model.CustomResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public CustomResponseModel healthCheck(){
        CustomResponseModel responseModel = new CustomResponseModel();
        responseModel.setBody("UP");
        return responseModel;
    }
}
