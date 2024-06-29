package ru.yuzhakov.web_client.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yuzhakov.web_client.model.Order;
import ru.yuzhakov.web_client.model.Settings;
import ru.yuzhakov.web_client.model.api.StorageApi;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class SettingsService {
    private final StorageApi storageApi;

    public SettingsService(StorageApi storageApi) {
        this.storageApi = storageApi;
    }

    public List<Settings> getAllSettings() {
        RestTemplate template = new RestTemplate();
        String url = storageApi.getBasicUri() + "settings";
        ResponseEntity<List<Settings>> response = template.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public Settings getSettingsById(Long id) {
        RestTemplate template = new RestTemplate();
        String url = storageApi.getBasicUri() + "settings/getById/" + id;
        ResponseEntity<Settings> response = template.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public void editSettings(Settings settings, boolean isSettingsExist) {
        RestTemplate template = new RestTemplate();
        String url = storageApi.getBasicUri() + "settings";
        if (isSettingsExist) url += "/update";
        else url += "/create";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Settings> entity = new HttpEntity<>(settings, headers);
        template.postForEntity(url, entity, Settings.class); //отправляем POST запрос
    }

    public void delete(Long id) {
        String url = storageApi.getBasicUri() + "settings/delete/" + id;
        RestTemplate template = new RestTemplate();
        template.getForEntity(url, Long.class);
    }
}
