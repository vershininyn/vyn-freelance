package com.github.onechesz.scooter_shop.config;

import com.github.onechesz.scooter_shop.services.UserService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userDetailsService;

    @Contract(pure = true)
    public SecurityConfig(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> {
                    authorizeHttpRequestsCustomizer.requestMatchers("/login", "/register").anonymous();
                    authorizeHttpRequestsCustomizer.requestMatchers("/scooters/*/edit", "/scooters/*/delete", "/scooters/add", "/users/**").hasRole("ADMIN");
                    authorizeHttpRequestsCustomizer.requestMatchers("/rents", "/rest_rents").authenticated();
                    authorizeHttpRequestsCustomizer.anyRequest().permitAll();
                })
                .formLogin(formLoginCustomizer -> {
                    formLoginCustomizer.loginPage("/login");
                    formLoginCustomizer.loginProcessingUrl("/login");
                    formLoginCustomizer.failureUrl("/login");
                    formLoginCustomizer.defaultSuccessUrl("/", true);
                })
                .logout(logoutCustomizer -> {
                    logoutCustomizer.logoutUrl("/logout");
                    logoutCustomizer.logoutSuccessUrl("/login");
                })
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.disable())
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
