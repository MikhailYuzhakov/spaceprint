package ru.yuzhakov.web_client.model.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("api.storage")
public class StorageApi {
    private String basicUri;

    public StorageApi(String basicUri) {
        this.basicUri = basicUri;
    }

    public StorageApi() {
    }

    public String getBasicUri() {
        return basicUri;
    }

    public void setBasicUri(String basicUri) {
        this.basicUri = basicUri;
    }
}
