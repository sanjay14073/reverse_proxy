package com.example.reverse.proxy.controller;

import com.example.reverse.proxy.model.CustomResponseModel;
import com.example.reverse.proxy.service.ProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rpxy")
@RequiredArgsConstructor
public class ProxyController {

    private final ProxyService proxyService;

    @GetMapping("/{*remainingPath}")
    public CustomResponseModel fwdGetRequest(@PathVariable String remainingPath){
        return proxyService.processMappings(remainingPath, HttpMethod.GET, Optional.empty());
    }

    @PostMapping("/{*remainingPath}")
    public CustomResponseModel fwdPostRequest(@PathVariable String remainingPath, @RequestBody RequestBody body){
        return proxyService.processMappings(remainingPath,HttpMethod.POST, Optional.ofNullable(body));
    }

    @PatchMapping("/{*remainingPath}")
    public CustomResponseModel fwdpatchRequest(@PathVariable String remainingPath,@RequestBody RequestBody body){
        return proxyService.processMappings(remainingPath,HttpMethod.PATCH,Optional.ofNullable(body));
    }

    @DeleteMapping("/{*remainingPath}")
    public CustomResponseModel fwdDeleteRequest(@PathVariable String remainingPath,@RequestBody RequestBody body){
        return proxyService.processMappings(remainingPath,HttpMethod.DELETE,Optional.ofNullable(body));
    }
    @PutMapping("/{*remainingPath}")
    public CustomResponseModel fwdequest(@PathVariable String remainingPath, @RequestBody RequestBody body){
        return proxyService.processMappings(remainingPath,HttpMethod.PUT,Optional.ofNullable(body));
    }

}
