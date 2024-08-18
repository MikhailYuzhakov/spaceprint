package ru.yuzhakov.order_storage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yuzhakov.order_storage.model.Settings;
import ru.yuzhakov.order_storage.service.SettingsService;

import java.util.List;

@RestController
@RequestMapping("/settings")
public class RestSettingsController {
    private final SettingsService service;

    public RestSettingsController(SettingsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Settings>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Settings> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Settings> create(@RequestBody Settings settings) {
        service.add(settings);
        return ResponseEntity.ok().body(settings);
    }

    @PostMapping("/update")
    public void update(@RequestBody Settings settings) {
        service.update(settings, settings.getId());
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
