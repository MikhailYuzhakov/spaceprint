package ru.yuzhakov.orders_handler.service;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yuzhakov.orders_handler.model.Settings;
import ru.yuzhakov.orders_handler.model.api.SettingsApi;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class SettingsService {
    private final SettingsApi settingsApi;

    public List<Settings> getAllSettings() {
        RestTemplate template = new RestTemplate();
        String url = settingsApi.getBasicUri();
        ResponseEntity<List<Settings>> response = template.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public Settings getSettingsById(Long id) {
        RestTemplate template = new RestTemplate();
        String url = settingsApi.getBasicUri() + "/getById/" + id;
        ResponseEntity<Settings> response = template.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public void editSettings(Settings settings, boolean isSettingsExist) {
        RestTemplate template = new RestTemplate();
        String url = settingsApi.getBasicUri();
        if (isSettingsExist) url += "/update";
        else url += "/create";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Settings> entity = new HttpEntity<>(settings, headers);
        template.postForEntity(url, entity, Settings.class); //отправляем POST запрос
    }

    public void delete(Long id) {
        String url = settingsApi.getBasicUri() + "/delete/" + id;
        RestTemplate template = new RestTemplate();
        template.getForEntity(url, Long.class);
    }
}
