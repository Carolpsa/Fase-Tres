package br.com.fiap.fasetres.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import br.com.fiap.fasetres.service.CustomUserDetailsService;
import br.com.fiap.fasetres.service.PlainTextPasswordEnconder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity  
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService ) {
        this.userDetailsService = customUserDetailsService;
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PlainTextPasswordEnconder(); 
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> 
            authorizeRequests
                .requestMatchers("/error","/h2-console/**").permitAll()
                .requestMatchers("/paciente/**").hasRole("PACIENTE")
                .requestMatchers("/medico/**").hasRole("MEDICO")
                .requestMatchers("/enfermeiro/**").hasRole("ENFERMEIRO")
                .anyRequest().authenticated()
        ).formLogin(form -> form.successHandler(
                customAuthenticationSuccessHandler())
            ).logout(logout -> logout.permitAll());

        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        return http.build();
    }

    @Bean 
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler(){
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication)
                    throws IOException, ServletException {
                var authorities = authentication.getAuthorities();
                if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE"))) {
                    response.sendRedirect("/paciente");
                } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO"))) {
                    response.sendRedirect("/medico");
                } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ENFERMEIRO"))) {
                    response.sendRedirect("/enfermeiro");
                } else {
                    response.sendRedirect("/error");
                }
            }
        };
    }
}
