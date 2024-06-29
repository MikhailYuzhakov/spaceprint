package ru.yuzhakov.resource_server.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 * Контроллер сервера ресурсов.
 */
@RestController
public class ResourceController {

    private final String UPLOAD_DIR = "C:\\Users\\OBI.DGZKS\\Desktop\\taskmanager\\10_IT\\printspace\\resource-server\\media\\";

    @PostMapping
    public String getImage() {
        return "notImage";
    }

    /**
     * Получение изображения кота.
     * @return ответ с массивом байтов.
     * @throws IOException исключения при обработке файла с изображением.
     */
    @PostMapping("/{uriImage}")
    public ResponseEntity<byte[]> getImageCat(@PathVariable("uriImage") String uriImage) throws IOException {
        String path = "C:\\Users\\OBI.DGZKS\\Desktop\\taskmanager\\10_IT\\printspace\\resource-server\\media\\" + uriImage;
        InputStream in = new FileInputStream(path);
        byte[] image = in.readAllBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @PostMapping("/download/{uriImage}")
    public void uploadImage(@RequestBody byte[] imageBytesArray, @PathVariable String uriImage) throws IOException {
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(UPLOAD_DIR + uriImage))) {
            out.write(imageBytesArray);
        }
    }
}
