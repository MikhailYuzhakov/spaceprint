package ru.yuzhakov.order_storage.actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import ru.yuzhakov.order_storage.repository.OrdersRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Endpoint(id = "getDatabaseMetrics")
public class DatabaseEndpoint implements DatabaseMetricsProvider {
    private final OrdersRepository repository;

    public DatabaseEndpoint(OrdersRepository repository) {
        this.repository = repository;
    }

    @Override
    @ReadOperation
    public List<DatabaseMetrics> getMetrics() {
        List<DatabaseMetrics> metrics = new ArrayList<>();
        metrics.add(new DatabaseMetrics("orders_qty", repository.countOrders()));
        metrics.add(new DatabaseMetrics("accounts_qty", repository.countAccounts()));
        metrics.add(new DatabaseMetrics("services_qty", repository.countServices()));
        metrics.add(new DatabaseMetrics("payment_settings_qty", repository.countPaymentSettings()));
        return metrics;
    }
}
