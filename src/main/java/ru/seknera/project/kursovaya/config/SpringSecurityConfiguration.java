package ru.seknera.project.kursovaya.config;

import org.springframework.security.config.Customizer;
import ru.seknera.project.kursovaya.model.UserAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .requestMatchers("/registration", "/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/budgets/**").hasAnyAuthority(UserAuthority.USER.getAuthority())
                                .requestMatchers(HttpMethod.POST, "/budgets/**").hasAnyAuthority(UserAuthority.USER.getAuthority())
                                .requestMatchers(HttpMethod.PUT, "/budgets/**").hasAnyAuthority(UserAuthority.USER.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/budgets/**").hasAnyAuthority(UserAuthority.USER.getAuthority())
                                .anyRequest().hasAuthority(UserAuthority.ADMIN.getAuthority()))

                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}