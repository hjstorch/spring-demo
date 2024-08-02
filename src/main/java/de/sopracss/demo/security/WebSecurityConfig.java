package de.sopracss.demo.security;

import de.sopracss.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.io.IOException;
import java.util.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    @Scope("prototype")
    public MvcRequestMatcher.Builder mvcMatcher(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder matcher) throws Exception {
        http
                // ...
                .authorizeHttpRequests( authorizeRequestCustomizer ->
                        authorizeRequestCustomizer
                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                .requestMatchers("/greeting").permitAll()
                                .requestMatchers("/greeting/**").permitAll()
                                .requestMatchers(matcher.pattern("/greetingRest")).permitAll()
                                .requestMatchers(matcher.pattern("/user")).hasRole(Roles.USER.name())
                                .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .headers(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
                // ...
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://example.com"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                try {
                    de.sopracss.demo.user.model.User loadedUser = userService.getUser(username);
                    return User.builder()
                            .username(username)
                            .roles(Roles.USER.name())
                            .build();

                } catch (IOException | NoSuchElementException e) {
                    throw new UsernameNotFoundException("User does not exist");
                }
            }
        };
    }


    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder defaultDelegatingPasswordEncoder)
            throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(defaultDelegatingPasswordEncoder)
                .withUser("user").password("user").roles(Roles.USER.name()).and()
                .withUser("admin").password("admin").roles(Roles.ADMIN.name());
    }

    @Bean
    @ApplicationScope
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
