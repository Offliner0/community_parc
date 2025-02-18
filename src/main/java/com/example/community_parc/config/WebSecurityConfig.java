package com.example.community_parc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrfConfig) -> csrfConfig.disable())

                .headers((headersConfig) ->
                        headersConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()))

                .authorizeHttpRequests((authorizeRequests)-> authorizeRequests
                        .requestMatchers("/", "/login", "/logout", "/error", "/error/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasRole("USER")
                        .anyRequest().authenticated()
                )

                .logout(logout ->
                        logout
                                .logoutUrl("/auth/logout") // 로그아웃 URL
//                                .logoutSuccessUrl("/login?logout") // 로그아웃 성공 시 리다이렉트 URL
                                .invalidateHttpSession(true) // 세션 무효화
                                .permitAll()
                );

        http
                .formLogin(formLogin -> formLogin.loginPage("/api/login")
                        .loginProcessingUrl("/api/loginProc"));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
}
