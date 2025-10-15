package dev.ohhoonim.demo_restclient.jsonPlaceholder.port;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "post-api")
public record PostApiProps(String url) {
    
}
