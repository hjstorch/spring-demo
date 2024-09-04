package de.sopracss.demo.user.service;

import de.sopracss.demo.monitoring.MetricsService;
import de.sopracss.demo.user.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Scope("singleton")
@Qualifier("contextQualifier")
@Component("userService")
@Slf4j
public class UserService {

    private Map<String, User> users = new HashMap<>();

    private final AtomicInteger userTotalCount = new AtomicInteger();

    private final Resource userfile;
    private final ObjectMapper mapper;
    private final MeterRegistry meterRegistry;
    private final MetricsService metricsService;

    public UserService(@Value("${demo.user.file}")Resource userfile, ObjectMapper mapper,
                       MeterRegistry meterRegistry, MetricsService metricsService
    ) {
        this.userfile = userfile;
        this.mapper = mapper;
        this.meterRegistry = meterRegistry;
        this.metricsService = metricsService;

        Gauge.builder("users", userTotalCount, AtomicInteger::get)
                .tags(Tags.of("type","size"))
                .register(this.meterRegistry);
    }

    @PostConstruct // alternative activate UserLoader
    public void loadUsers() throws IOException {
        log.info("Loading users from: {}", userfile.getFilename());
        List<User> loadedUsers =  mapper.readValue(
                userfile.getContentAsByteArray(),
                new TypeReference<List<User>>() {}
        );
        users = loadedUsers.stream().collect(
                HashMap::new,
                (map, user) -> map.put(user.getUsername(), user),
                HashMap::putAll
        );
        meterRegistry.gaugeMapSize("users", Tags.of("type", "autocount"), this.users);
        userTotalCount.set(this.users.size());
    }

    private void saveUsers() throws IOException {
        log.info("Saving users to: {}", userfile.getFilename());
        mapper.writeValue(userfile.getFile(), List.copyOf(users.values()));
    }

    public List<User> listUsers() {
        return List.copyOf(users.values());
    }

    public void addUser(User user) throws IllegalArgumentException {
        if(users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("User already exists");
        }
        users.put(user.getUsername(),user);
        try {
            this.saveUsers();
            metricsService.incrementUserAddedTodayCounter();
            userTotalCount.set(this.users.size());
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not save user", e);
        }
    }

    public User getUser(String username) throws NoSuchElementException, IOException {
        if(users.isEmpty()){
            this.loadUsers();
        }
        return Optional.ofNullable(users.get(username)).orElseThrow();
    }
}
