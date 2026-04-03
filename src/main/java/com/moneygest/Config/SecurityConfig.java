package com.moneygest.Config;

import com.moneygest.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        // Mantenemos texto plano para desarrollo
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // 1. Recursos públicos: agregué /recuperar/** por si tienes subrutas
                        .requestMatchers("/", "/login", "/recuperar/**", "/css/**", "/img/**", "/js/**").permitAll()

                        // 2. RUTAS DE ADMINISTRACIÓN: El filtro debe ser estricto
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 3. Todo lo demás requiere login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/") // Tu landing que tiene el form de login
                        .loginProcessingUrl("/login") // El 'action' de tu <form>
                        .usernameParameter("username") // Debe coincidir con el name="username" del HTML
                        .passwordParameter("password") // Debe coincidir con el name="password" del HTML
                        .defaultSuccessUrl("/menuPrincipal", true)
                        .failureUrl("/?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}