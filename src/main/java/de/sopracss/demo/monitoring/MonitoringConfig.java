package de.sopracss.demo.monitoring;

import de.sopracss.demo.quote.QuoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MonitoringConfig {

    @Value("${application.title}")
    String appName;

    @Value("${application.version}")
    String appVersion;

    @Bean // actuator/info
    public InfoContributor infoContributor() {
        // using lambda representing the only interface function instead of creating anonymous inner class
        return builder -> builder
                .withDetail("application", appName)
                .withDetail("version", appVersion )
                .withDetails(Map.of("key of info", "info value to display" ));
    }

    @Bean("quote") // actuator/health/quote
    public HealthIndicator quoteHealthIndicator(QuoteService quoteService) {
        return () -> "No quote available".equals(quoteService.getQuote()) ?
                Health.outOfService().withDetail("QuoteService", "unreachable").build() :
                Health.up().withDetail("QuoteService", "available").build();
    }
}
