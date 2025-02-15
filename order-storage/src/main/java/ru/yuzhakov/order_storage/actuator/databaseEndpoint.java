package ru.yuzhakov.order_storage.actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import ru.yuzhakov.order_storage.repository.OrdersRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Endpoint(id = "getDatabaseMetrics")
public class databaseEndpoint {
    private final OrdersRepository repository;
    private final Counter requestsCounter;

    public databaseEndpoint(OrdersRepository repository, MeterRegistry meterRegistry) {
        this.repository = repository;
        requestsCounter = meterRegistry.counter("db_metrics_counter");
    }

    @ReadOperation
    public List<DatabaseMetrics> getMetrics() {
        List<DatabaseMetrics> metrics = new ArrayList<>();
        metrics.add(new DatabaseMetrics("orders_qty", repository.countOrders()));
        metrics.add(new DatabaseMetrics("accounts_qty", repository.countAccounts()));
        metrics.add(new DatabaseMetrics("services_qty", repository.countServices()));
        metrics.add(new DatabaseMetrics("payment_settings_qty", repository.countPaymentSettings()));
        requestsCounter.increment();
        return metrics;
    }

    public static class DatabaseMetrics {
        private String name;
        private Integer rowCount;

        public DatabaseMetrics(String name, Integer rowCount) {
            this.name = name;
            this.rowCount = rowCount;
        }

        public String getName() {
            return name;
        }

        public Integer getRowCount() {
            return rowCount;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRowCount(Integer rowCount) {
            this.rowCount = rowCount;
        }
    }
}
