package de.sopracss.demo.monitoring;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MetricsService implements ApplicationRunner {

    public static final String USERS_ADDED_TODAY_COUNTER_NAME = "users.added.today";
    public static final Iterable<Tag> USERS_ADDED_TODAY_COUNTER_TAGS = List.of(Tag.of("type","countedDaily"));

    private final MeterRegistry meterRegistry;

    public MetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // register and initialize custom Metrics
        // use a gauge because we want to reset them daily
        this.meterRegistry.gauge(
                USERS_ADDED_TODAY_COUNTER_NAME,
                USERS_ADDED_TODAY_COUNTER_TAGS,
                new AtomicInteger(0))
                .set(0);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetDailyMetrics() {
        this.meterRegistry
                // this may seem strange: if gauge exists the value is not changed
                .gauge(USERS_ADDED_TODAY_COUNTER_NAME, new AtomicInteger(0))
                // we need to call the set() explicit
                .set(0);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void resetHourlyMetrics() {
        // ...
    }

    public void incrementGauge(String name, Iterable<Tag> tags) {
        Objects.requireNonNull(this.meterRegistry.gauge(name, tags, new AtomicInteger(0))).incrementAndGet();
    }

    public void decrementGauge(String name, Iterable<Tag> tags) {
        Objects.requireNonNull(this.meterRegistry.gauge(name, tags, new AtomicInteger(0))).decrementAndGet();
    }
}
