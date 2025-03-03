package ru.yuzhakov.orders_handler.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yuzhakov.orders_handler.model.Settings;
import ru.yuzhakov.orders_handler.service.SettingsService;

import java.util.List;

@Controller
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {
    private final SettingsService service;
    private final String redirectURI = "http://localhost:8080";

    @GetMapping
    public String getSettingsPage(Model model) {
        List<Settings> settings = service.getAllSettings();
        if (settings.isEmpty()) {
            return "redirect:" + redirectURI + "/settings/create";
        } else {
            model.addAttribute("settings", service.getAllSettings());
            return "settings/settings-list";
        }
    }

    @GetMapping("/create")
    public String getSettingsCreateForm(Settings settings) {
        return "settings/settings-create";
    }

    @PostMapping("/create")
    public String settingsCreate(Settings settings) {
        service.editSettings(settings, false);
        return "redirect:" + redirectURI + "/settings";
    }

    @GetMapping("/update/{id}")
    public String getSettingsUpdateForm(@PathVariable("id") Long id, Model model) {
        Settings settings = service.getSettingsById(id);
        model.addAttribute("settings", settings);
        return "settings/settings-update";
    }

    @PostMapping("/update")
    public String settingsUpdate(@ModelAttribute("settings") Settings settings) {
        service.editSettings(settings, true);
        return "redirect:" + redirectURI + "/settings";
    }

    @GetMapping("/delete/{id}")
    public String settingsDelete(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:" + redirectURI + "/settings";
    }
}
