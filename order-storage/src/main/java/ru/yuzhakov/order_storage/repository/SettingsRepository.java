package ru.yuzhakov.order_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yuzhakov.order_storage.model.Settings;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {}
