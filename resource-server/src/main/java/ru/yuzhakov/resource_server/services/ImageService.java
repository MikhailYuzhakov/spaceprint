package ru.yuzhakov.resource_server.services;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yuzhakov.resource_server.model.api.LocalStorageApi;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final LocalStorageApi localStorageApi;

    public void getImageAsBase64(String uriImage, HttpServletResponse response) {
        try {
            // Проверка, существует ли файл по указанному пути
            Path path = Paths.get(localStorageApi.getLocalPath() + uriImage);
            if (!Files.exists(path)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                response.getWriter().write("{\"image\":\"Image not found\"}");
                return;
            }

            // Чтение изображения в массив байтов
            byte[] imageBytes = Files.readAllBytes(path);

            // Кодирование в Base64
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Установка типа контента и отправка ответа
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
            response.getWriter().write("{\"image\": \"" + base64Image + "\"}");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            e.printStackTrace();
        }
    }

    public void uploadImage(byte[] imageBytesArray, String uriImage) {
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(localStorageApi.getLocalPath() + uriImage))) {
            out.write(imageBytesArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String uriImage, HttpServletResponse response) {
        Path path = Paths.get(localStorageApi.getLocalPath() + uriImage);
        try {
            if (!Files.exists(path)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                response.getWriter().write("{\"image\":\"Image not found\"}");
            } else {
                Files.delete(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
