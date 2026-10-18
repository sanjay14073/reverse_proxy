package com.example.reverse.proxy.config;

import com.example.reverse.proxy.model.ProxyModel;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClient;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "proxy")
@Slf4j
public class ProxyLoaderConfig {
    private List<ProxyModel> routes;
}
