package de.sopracss.demo.quote;

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

    @Autowired
    public QuoteService(
            RestTemplate restTemplate,
            @Value("${quote.host:http://api.quotable.io}") String quoteApi,
            @Value("${quote.path:/quotes/random}") String quotePath) {
        this.restTemplate = restTemplate;
        this.quoteApi = quoteApi;
        this.quotePath = quotePath;
    }

    public String getQuote() {
        Quote[] quotes = restTemplate.getForObject(quoteApi + quotePath, Quote[].class);
        return Optional.ofNullable(quotes[0])
                .map(Quote::getContent)
                .orElse("No quote available");
    }
}
