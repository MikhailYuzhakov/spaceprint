package ru.yuzhakov.web_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yuzhakov.web_client.model.Settings;
import ru.yuzhakov.web_client.service.SettingsService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/settings")
public class SettingsController {
    private final SettingsService service;

    public SettingsController(SettingsService service) {
        this.service = service;
    }

    @GetMapping
    public String getSettingsPage(Model model) {
        List<Settings> settings = service.getAllSettings();
        if (settings.isEmpty()) {
            return "redirect:/settings/create";
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
        return "redirect:/settings";
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
        return "redirect:/settings";
    }

    @GetMapping("/delete/{id}")
    public String settingsDelete(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/settings";
    }
}
