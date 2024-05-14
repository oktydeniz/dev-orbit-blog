package com.odeniz.dev.orbit.security;


import com.odeniz.dev.orbit.configration.CustomLoginFailureHandler;
import com.odeniz.dev.orbit.configration.CustomLoginSuccessHandler;
import com.odeniz.dev.orbit.configration.JWTAuthenticationEntrypoint;
import com.odeniz.dev.orbit.configration.JwtAuthFilter;
import com.odeniz.dev.orbit.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    @Autowired
    private CustomLoginFailureHandler loginFailureHandler;

    @Autowired
    private CustomLoginSuccessHandler loginSuccessHandler;

    public static String[] NON_LOGIN_REQUIRED_URLS = { "/robot*", "/test/**", "/chart-test/**", "/captcha/**",
            "/register/**", "/password/**", "/login/**", "/webjars/**", "/terms/**", "/static/**", "/stylesheets/**", "/js/**", "/registration-confirmation/**",
            "/orbit/**", "/profile/**", "/**"};

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JWTAuthenticationEntrypoint entrypoint;

    @Bean
    public JwtAuthFilter jwtAuthenticationFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedOrigins(List.of("http://localhost:8080/"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement((s) -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .formLogin(f -> f.loginPage("/login").usernameParameter("email").passwordParameter("password").failureHandler(loginFailureHandler).successHandler(loginSuccessHandler).permitAll())
                .logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login").permitAll())
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(NON_LOGIN_REQUIRED_URLS).permitAll();
                    request.requestMatchers("/api/auth/**").permitAll()
                            .anyRequest().authenticated();
                }).exceptionHandling((e) -> e.authenticationEntryPoint(entrypoint))
                .rememberMe(Customizer.withDefaults());
        http.addFilterBefore(this.jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
