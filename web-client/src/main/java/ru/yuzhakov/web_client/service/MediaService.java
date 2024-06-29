package ru.yuzhakov.web_client.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yuzhakov.web_client.model.api.MediaApi;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

@Service
public class MediaService {
    private final MediaApi mediaApi;

    public MediaService(MediaApi mediaApi) {
        this.mediaApi = mediaApi;
    }

    /**
     * Получает изображение с сервара ресурсов.
     * @param path путь к изображению.
     * @return изображение в виде строки.
     */
    public String getImage(String path) {
        RestTemplate template = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        String url = mediaApi.getBasicUri() + path;
        ResponseEntity<byte[]> response = template.exchange(url, HttpMethod.POST, entity, byte[].class);
        return Base64.getEncoder().encodeToString(response.getBody());
    }
}
