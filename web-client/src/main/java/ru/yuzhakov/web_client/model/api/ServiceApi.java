package ru.yuzhakov.web_client.model.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("api.service")
public class ServiceApi {
    private String basicUri;

    public ServiceApi(String basicUri) {
        this.basicUri = basicUri;
    }

    public ServiceApi() {
    }

    public String getBasicUri() {
        return basicUri;
    }

    public void setBasicUri(String basicUri) {
        this.basicUri = basicUri;
    }
}
