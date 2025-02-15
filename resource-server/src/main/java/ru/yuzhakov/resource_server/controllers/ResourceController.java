package ru.yuzhakov.resource_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.yuzhakov.resource_server.services.FileGateway;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 * Контроллер сервера ресурсов.
 */
@RestController
@RequestMapping("/media")
public class ResourceController {

    //перенести эту переменную в конфиг
    private final String UPLOAD_DIR = "/media/yuzhakovmihail/DATA/04_GeekBrains/08_backend_java_spring/printspace/resource-server/media/";
    @Autowired
    private final FileGateway fileGateway;

    public ResourceController(FileGateway fileGateway) {
        this.fileGateway = fileGateway;
    }

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
        String path = UPLOAD_DIR + uriImage;
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
            fileGateway.writeToFile(uriImage , imageBytesArray); //просто демонстрирует возможность копирования изображения через spring integration
        }
    }
}
