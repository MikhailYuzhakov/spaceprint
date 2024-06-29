package ru.yuzhakov.web_client.model.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("api.payment")
public class PaymentApi {
    private String basicUri;

    public PaymentApi(String basicUri) {
        this.basicUri = basicUri;
    }

    public PaymentApi() {
    }

    public String getBasicUri() {
        return basicUri;
    }

    public void setBasicUri(String basicUri) {
        this.basicUri = basicUri;
    }
}
