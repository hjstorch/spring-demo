package de.sopracss.demo.quote;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class QuoteService {

    private final RestTemplate restTemplate;
    private final String quoteApi;
    private final String quotePath;

    private MeterRegistry registry = Metrics.globalRegistry;

    @Autowired
    public QuoteService(
            RestTemplate restTemplate,
            @Value("${quote.host:http://api.quotable.io}") String quoteApi,
            @Value("${quote.path:/quotes/random}") String quotePath) {
        this.restTemplate = restTemplate;
        this.quoteApi = quoteApi;
        this.quotePath = quotePath;

        Timer quoteTimer = Timer.builder("quote.service")
                .register(registry);
        quoteTimer.record(this::getQuote);
    }

    public String getQuote() {
        Quote[] quotes = restTemplate.getForObject(quoteApi + quotePath, Quote[].class);
        return Optional.ofNullable(quotes[0])
                .map(Quote::getContent)
                .orElse("No quote available");
    }
}
