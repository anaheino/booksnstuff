package com.example.bookapp.config;

import com.example.common.config.CommonSecurityConfiguration;
import com.example.common.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.common.web.BaseAppUrlSchema.LOGIN;
import static com.example.common.web.BaseAppUrlSchema.LOGIN_FORM;
import static com.example.common.web.BaseAppUrlSchema.LOGOUT;
import static com.example.common.web.BaseAppUrlSchema.REGISTER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration extends CommonSecurityConfiguration {

    @Value("${userapp.url}")
    private String loginUrl;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, UserDetailsService userService) {
        super(userService);
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(LOGIN_FORM, LOGIN, REGISTER)
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .formLogin(form -> form
                        .loginPage(loginUrl + LOGIN_FORM)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl(LOGOUT)
                        .deleteCookies("JWT_TOKEN")
                        .permitAll())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}