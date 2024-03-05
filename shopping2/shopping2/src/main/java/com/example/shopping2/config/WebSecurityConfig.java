package com.example.shopping2.config;

import com.example.shopping2.jwt.JwtTokenFilter;
import com.example.shopping2.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsManager manager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
            http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(
                            auth -> auth
                                    .requestMatchers(
                                            "/token/issue",
                                            "/token/validate",
                                            "/token/signup"
                                    )
                                    .permitAll()

                                    .requestMatchers("/token/profile")
                                    .authenticated()

                                    .requestMatchers("/articles", "articles/read/{id}",  "articles/read", "articles/{id}/edit", "articles/{id}/delete")
                                    .hasAnyRole("USER", "ADMIN")

                                    .requestMatchers("/auth/admin-role")
                                    .hasRole("ADMIN")

                                    .requestMatchers("/auth/read-authority")
                                    .hasAnyAuthority("READ_AUTHORITY", "WRITE_AUTHORITY")

                                    .requestMatchers("/auth/write-authority")
                                    .hasAuthority("WRITE_AUTHORITY")

                                    .anyRequest()
                                    .permitAll()
                    )
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(
                            new JwtTokenFilter(
                                    jwtTokenUtils,
                                    manager
                            ),
                            AuthorizationFilter.class
                    )
                    ;
            return http.build();
    }

}
