package ru.yuzhakov.order_storage.actuator.decorators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yuzhakov.order_storage.actuator.DatabaseEndpoint;
import ru.yuzhakov.order_storage.actuator.DatabaseMetrics;
import ru.yuzhakov.order_storage.actuator.DatabaseMetricsProvider;

import java.util.List;

@Component
public abstract class AbstractDecorator implements DatabaseMetricsProvider {
    @Autowired
    protected DatabaseEndpoint databaseEndpoint;

    public AbstractDecorator(DatabaseEndpoint databaseEndpoint) {
        this.databaseEndpoint = databaseEndpoint;
    }

    @Override
    public List<DatabaseMetrics> getMetrics() {
        return databaseEndpoint.getMetrics();
    }
}
