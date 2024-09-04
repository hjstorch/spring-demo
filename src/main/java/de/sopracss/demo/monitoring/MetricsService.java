package de.sopracss.demo.monitoring;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MetricsService implements ApplicationRunner {

    public static final String USERS_ADDED_TODAY_COUNTER_NAME = "users.added.today";
    public static final Iterable<Tag> USERS_ADDED_TODAY_COUNTER_TAGS = List.of(Tag.of("type","countedDaily"));

    private final MeterRegistry meterRegistry;

    private final AtomicInteger userAddedTodayCounterValue = new AtomicInteger(0); // hold strong reference here

    public MetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // register and initialize custom Metrics
        // use a gauge because we want to reset them daily
        Gauge.builder(USERS_ADDED_TODAY_COUNTER_NAME, userAddedTodayCounterValue, AtomicInteger::get)
                .tags(USERS_ADDED_TODAY_COUNTER_TAGS)
                .register(meterRegistry);
    }

    @Scheduled(cron = "0 30 1 * * *")
    public void resetMetricsDailyAt1H30() {
        userAddedTodayCounterValue.set(0);
    }

    @Scheduled(cron = "0 0 * * * *") // equal to "0 0 */1 * * *"
    public void resetMetricsHourly() {
        // ...
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 10)
    public void resetMetricsEvery10Min() {
        // ...
    }

    // not good because we are creating a Meter with weak references in micrometer gauge
//    public void incrementGauge(String name, Iterable<Tag> tags) {
//        Objects.requireNonNull(this.meterRegistry.gauge(name, tags, new AtomicInteger(0))).incrementAndGet();
//    }
//
//    public void decrementGauge(String name, Iterable<Tag> tags) {
//        Objects.requireNonNull(this.meterRegistry.gauge(name, tags, new AtomicInteger(0))).decrementAndGet();
//    }

    // better: use strong reference maintained in this service
    public synchronized void incrementUserAddedTodayCounter() {
        userAddedTodayCounterValue.incrementAndGet();
    }
}
