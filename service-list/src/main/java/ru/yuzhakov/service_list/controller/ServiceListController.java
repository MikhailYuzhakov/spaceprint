package ru.yuzhakov.service_list.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yuzhakov.service_list.model.MyService;
import ru.yuzhakov.service_list.service.ServiceListService;

import java.util.List;

@RestController
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
}
