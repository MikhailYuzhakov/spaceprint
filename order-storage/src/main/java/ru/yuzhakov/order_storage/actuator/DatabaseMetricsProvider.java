package ru.yuzhakov.order_storage.actuator;

import java.util.List;

public interface DatabaseMetricsProvider {
    List<DatabaseMetrics> getMetrics();
}
