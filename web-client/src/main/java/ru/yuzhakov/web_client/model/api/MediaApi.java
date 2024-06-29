package ru.yuzhakov.web_client.model.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("api.media")
public class MediaApi
{
    private String basicUri;

    public MediaApi(String basicUri) {
        this.basicUri = basicUri;
    }

    public MediaApi() {
    }

    public String getBasicUri() {
        return basicUri;
    }

    public void setBasicUri(String basicUri) {
        this.basicUri = basicUri;
    }
}
