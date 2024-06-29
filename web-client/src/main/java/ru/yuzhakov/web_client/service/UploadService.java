package ru.yuzhakov.web_client.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.yuzhakov.web_client.model.api.MediaApi;

import java.io.IOException;

@Service
public class UploadService {
    private final MediaApi mediaApi;

    public UploadService(MediaApi mediaApi) {
        this.mediaApi = mediaApi;
    }

    public void uploadFile(MultipartFile file, String filename) throws IOException {
        String url = mediaApi.getBasicUri() + "download/" + filename;

        byte[] imageByteArray = file.getBytes();
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);
        HttpEntity<byte[]> entity = new HttpEntity<>(imageByteArray, headers);
        template.postForEntity(url, entity, byte[].class); //отправляем POST запрос
    }
}
