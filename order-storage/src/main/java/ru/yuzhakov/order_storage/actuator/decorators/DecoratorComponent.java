package ru.yuzhakov.order_storage.actuator.decorators;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import ru.yuzhakov.order_storage.actuator.DatabaseEndpoint;
import ru.yuzhakov.order_storage.actuator.DatabaseMetrics;

import java.util.List;

@Component
public class DecoratorComponent extends AbstractDecorator {
    private final Counter requestsCounter;

    public DecoratorComponent(DatabaseEndpoint databaseEndpoint, MeterRegistry meterRegistry) {
        super(databaseEndpoint);
        requestsCounter = meterRegistry.counter("db_metrics_counter");
    }

    @Override
    public List<DatabaseMetrics> getMetrics() {
        requestsCounter.increment(); //добавляем метрику, не изменяя исходного кода (Decorator)
        return super.getMetrics();
    }
}
