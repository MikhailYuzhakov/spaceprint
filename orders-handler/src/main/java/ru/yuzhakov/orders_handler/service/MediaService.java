package ru.yuzhakov.orders_handler.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.yuzhakov.orders_handler.model.api.MediaApi;
import ru.yuzhakov.orders_handler.model.common.ImageClass;

@Service
@AllArgsConstructor
public class MediaService {
    private final MediaApi mediaApi;
    private static final Logger logger = LoggerFactory.getLogger(MediaService.class);

    /**
     * Получает изображение с сервера ресурсов.
     * @param imageUUID уникальный идентификатор изображения.
     * @return изображение в виде строки.
     */
    public String getImage(String imageUUID) {
        RestTemplate template = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        String imageUri = mediaApi.getDownloadUri() + imageUUID;
        try {
            ResponseEntity<ImageClass> response = template.exchange(imageUri, HttpMethod.GET, entity, ImageClass.class);
            ImageClass imageClass = response.getBody();

            if (imageClass != null) {
                return imageClass.getImage();
            } else {
                return "null";
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return "null";
            }
        } catch (RestClientException e) {
            System.err.println("Rest client error: " + e.getMessage());
        }
        return "null";
    }

    public void deleteImage(String imageUUID) {
        String deletedImageUri = mediaApi.getDeleteUri() + imageUUID;
        RestTemplate template = new RestTemplate();
        try {
            template.getForEntity(deletedImageUri, String.class);
        } catch (HttpClientErrorException e) {
            logger.info("{}{}{}\n", "Image ", deletedImageUri, " not found!");
        }
    }
}
