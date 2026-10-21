package com.example.reverse.proxy.service;

import com.example.reverse.proxy.config.ProxyLoaderConfig;

import com.example.reverse.proxy.model.ProxyModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProxyService {

    private final ProxyLoaderConfig proxyLoaderConfig;
    private final RestClient restClient = RestClient.create();

    public ResponseEntity<byte[]> processMappings(String url, HttpMethod method, Optional<byte[]>requestBody){
        log.info("hitting this url {}",url);
        if(url.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Route Not Found".getBytes());
        }
        return forwardRequest(url,method,requestBody);
    }

    private ResponseEntity<byte[]> forwardRequest(String url, HttpMethod method, Optional<byte[]> requestBody){
        List<ProxyModel> getAllProxyConfig = proxyLoaderConfig.getRoutes();
        Optional<ProxyModel> matchingUrlModel = getAllProxyConfig.stream()
                .filter(proxyModel -> proxyModel.getPath().equals(url))
                .findFirst();
        if(matchingUrlModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Route Not Found".getBytes());
        }
        ProxyModel matchingUrlProxyModel =matchingUrlModel.get();
        log.info("Supported methods http methods {}",matchingUrlProxyModel.getSupportMethods().toString());
        if (!matchingUrlProxyModel.getSupportMethods().contains(method)) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("Unsupported Method".getBytes());
        }
        String upStream=matchingUrlProxyModel.getUpstream();
        String httpMethodStr = method.name().toUpperCase();

        if (HttpMethod.GET.name().equals(httpMethodStr)) {
            return restClient.get()
                    .uri(upStream)
                    .retrieve()
                    .toEntity(byte[].class);
        }

        else if (HttpMethod.POST.name().equals(httpMethodStr)) {
            return restClient.post()
                    .uri(upStream)
                    .body(requestBody) // Appends the payload object
                    .retrieve()
                    .toEntity(byte[].class);
        }

        else if (HttpMethod.PUT.name().equals(httpMethodStr)) {
            return restClient.put()
                    .uri(upStream)
                    .body(requestBody)
                    .retrieve()
                    .toEntity(byte[].class);
        }

        else if (HttpMethod.PATCH.name().equals(httpMethodStr)) {
            return restClient.patch()
                    .uri(upStream)
                    .body(requestBody)
                    .retrieve()
                    .toEntity(byte[].class);
        }

        else if (HttpMethod.DELETE.name().equals(httpMethodStr)) {
            return restClient.delete()
                    .uri(upStream)
                    .retrieve()
                    .toEntity(byte[].class);
        }
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Oops! Something Went wrong on our side".getBytes());
    }


}
