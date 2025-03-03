package ru.yuzhakov.resource_server.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yuzhakov.resource_server.services.FileGateway;
import ru.yuzhakov.resource_server.services.ImageService;

import java.io.*;

/**
 * Контроллер сервера ресурсов.
 */
@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class ResourceController {
    private final ImageService imageService;

    @GetMapping("/download/{uriImage}")
    public void getImage(@PathVariable("uriImage") String uriImage, HttpServletResponse response) {
        imageService.getImageAsBase64(uriImage, response);
    }

    @GetMapping("/delete/{uriImage}")
    public void deleteImage(@PathVariable("uriImage") String uriImage, HttpServletResponse response) {
        imageService.delete(uriImage, response);
    }

    @PostMapping("/upload/{uriImage}")
    public void uploadImage(@RequestBody byte[] imageBytesArray, @PathVariable String uriImage) {
        imageService.uploadImage(imageBytesArray, uriImage);
    }


//    /**
//     * Получение изображения кота.
//     * @return ответ с массивом байтов.
//     * @throws IOException исключения при обработке файла с изображением.
//     */
//    @PostMapping("/{uriImage}")
//    public ResponseEntity<byte[]> getImage(@PathVariable("uriImage") String uriImage) throws IOException {
//        String path = UPLOAD_DIR + uriImage;
//        InputStream in = new FileInputStream(path);
//        byte[] image = in.readAllBytes();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        in.close();
//        return new ResponseEntity<>(image, headers, HttpStatus.OK);
//    }



//    @PostMapping("/download/{uriImage}")
//    public void uploadImage(@RequestBody byte[] imageBytesArray, @PathVariable String uriImage) throws IOException {
//        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(UPLOAD_DIR + uriImage))) {
//            out.write(imageBytesArray);
//            fileGateway.writeToFile(uriImage , imageBytesArray); //просто демонстрирует возможность копирования изображения через spring integration
//        }
//    }


}
