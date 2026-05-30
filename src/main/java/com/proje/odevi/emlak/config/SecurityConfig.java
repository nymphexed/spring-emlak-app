package com.proje.odevi.emlak.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.proje.odevi.emlak.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/satici/foto-sil/**") // DELETE için CSRF kapalı
                )
                .authenticationProvider(authProvider())
                .authorizeHttpRequests(auth -> auth
                        // ✔ Static dosyalar
                        .requestMatchers("/css/**", "/js/**", "/uploads/**", "/img/**", "/webjars/**").permitAll()

                        // ✔ Herkesin erişebileceği sayfalar
                        .requestMatchers("/", "/login", "/register", "/emlak", "/emlak/**").permitAll()

                        // ✔ Satıcıya özel işlemler
                        .requestMatchers("/satici/**").hasRole("SATICI")
                        .requestMatchers(HttpMethod.DELETE, "/satici/foto-sil/**").authenticated()
                        .requestMatchers("/profil/isletme-sil").hasRole("SATICI")

                        .requestMatchers("/favori/**").authenticated()
                        // ✔ Diğer tüm istekler → giriş gerektirir
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll());

        return http.build();
    }
}
