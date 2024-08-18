package ru.yuzhakov.service_list.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yuzhakov.service_list.model.MyService;
import ru.yuzhakov.service_list.service.ServiceListService;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceListController {
    private final ServiceListService service;

    public ServiceListController(ServiceListService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MyService>> getServices(){
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/getByUri/{uri}")
    public ResponseEntity<MyService> getServiceByUri(@PathVariable("uri") String uri) {
        return ResponseEntity.ok().body(service.getServiceByUri(uri));
    }

    @GetMapping("/{id}")
    public MyService getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.getServiceById(id)).getBody();
    }

    @PostMapping("/create")
    public ResponseEntity<MyService> createService(@RequestBody MyService myService) {
        service.add(myService);
        return ResponseEntity.ok().body(myService);
    }

    @PostMapping("/update")
    public void updateService(@RequestBody MyService myService) {
        service.update(myService, myService.getId());
    }

    @GetMapping("/delete/{id}")
    public void deleteService(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @GetMapping("/service-image-update/{id}/{imageUri}")
    public void updateImageUri(@PathVariable("imageUri") String imageUri, @PathVariable("id") Long id) {
        service.updateImageUri(imageUri, id);
    }

}
