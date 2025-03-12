package ru.yuzhakov.order_storage.service;

import org.springframework.stereotype.Service;
import ru.yuzhakov.order_storage.model.Settings;
import ru.yuzhakov.order_storage.repository.SettingsRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class SettingsService {
    private final SettingsRepository repository;

    public SettingsService(SettingsRepository repository) {
        this.repository = repository;
    }

    public List<Settings> getAll() {
        List<Settings> settings = repository.findAll();
        settings.sort(Comparator.comparing(Settings::getUpdateDate));
        return settings;
    }

    public Settings get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void add(Settings settings) {
        repository.save(settings);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void update(Settings settings, Long id) {
        if (repository.existsById(id)) {
            settings.setId(id);
            repository.save(settings);
        }
    }
}
