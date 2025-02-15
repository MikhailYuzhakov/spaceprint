package ru.yuzhakov.services_handler.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yuzhakov.services_handler.model.MyService;
import ru.yuzhakov.services_handler.service.FileGateway;
import ru.yuzhakov.services_handler.service.MediaService;
import ru.yuzhakov.services_handler.service.MyServiceService;
import ru.yuzhakov.services_handler.service.UploadService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
    private final MyServiceService service;
    private final MediaService mediaService;
    private final UploadService uploadService;
    private final FileGateway fileGateway;
    private String imageUri;
    private Long serviceId;

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("services", service.getAllServices());
        model.addAttribute("images", service.findAllImages());
        return "services";
    }

    @GetMapping("/{uri}")
    public String getServicePage(@PathVariable("uri") String uri, Model model) {
        MyService myService = service.getServiceByUri(uri);
        List<MyService> services = service.getAllServices();
        services.removeIf(service -> service.getUri().equals(uri));

        List<String> images = service.findAllImages();
        String image = service.findImage(myService);
        images.remove(image);

        model.addAttribute("service", myService);
        model.addAttribute("image", image);
        model.addAttribute("services", services);
        model.addAttribute("images", images);
        return "/services/service-description";
    }

    @GetMapping("/create")
    public String getCreateServiceForm(Model model, MyService myService) {
        model.addAttribute("service", myService);
        return "services/service-create";
    }

    @PostMapping("/create")
    public String createService(MyService myService) {
        service.editService(myService, false);
        fileGateway.writeToFile("service_" + myService.getName() + ".txt", myService.toString());
        return "redirect:/services";
    }

    @GetMapping("/update/{id}")
    public String getServiceUpdateForm(@PathVariable("id") Long id, Model model) {
        MyService myService = service.get(id);
        model.addAttribute("service", myService);
        imageUri = myService.getImageUri();
//        model.addAttribute("image", mediaService.getImage(imageUri));
        model.addAttribute("imageUri", imageUri);
        return "services/service-update";
    }

    @PostMapping("/update")
    public String updateService(@ModelAttribute("service") MyService myService) {
        myService.setImageUri(imageUri);
        service.editService(myService, true);
        imageUri = "";
        return "redirect:/services";
    }

    @GetMapping("/service-image-update/{id}")
    public String getImageUpdateForm(@PathVariable("id") Long id) {
        serviceId = id;
        return "/services/service-update-image";
    }

    //При обновлении картинки, удалять старое изображение из папки
    @PostMapping("/service-image-update")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + ".png";
        uploadService.uploadFile(file, filename);
        service.updateImageUri(filename, serviceId);
        return "redirect:/services";
    }

    //оплата услуги
    @GetMapping("/purchase/{id}")
    public String purchaseService(@PathVariable("id") Long id) {
        service.purchaseService(id);
        return "redirect:/services";
    }

    /**
     * Страница с ошибками в ходе покупки товара.
     * @param e объект исключения.
     * @param model модель для передачи данных представлению.
     * @return страницу с ошибками.
     */
    @ExceptionHandler(RuntimeException.class)
    public String errorPage(RuntimeException e, Model model){
        model.addAttribute("message", e.getMessage());
        return "redirect:/services";
    }
}
