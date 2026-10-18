package com.example.reverse.proxy.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.List;

@Getter
@Setter
public class ProxyModel {
    private String id;
    private String path;
    private String upstream;
    private List<HttpMethod> supportMethods;
}
