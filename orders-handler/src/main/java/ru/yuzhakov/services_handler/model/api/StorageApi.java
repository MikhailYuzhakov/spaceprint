package ru.yuzhakov.services_handler.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("api.storage")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StorageApi {
    private String basicUri;
}