package com.example.sess;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/tasks/**")
                .hasRole("USER")
                .and()
                .csrf().disable()
                .httpBasic();
        return http.build();
    }

    @Bean
    public UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails john = users
                .username("john")
                .password(passwordEncoder.encode("pwd123"))
                .roles("USER")
                .build();
        UserDetails jane = users
                .username("jane")
                .password(passwordEncoder.encode("abc456"))
                .roles("USER")
                .build();
        // UserDetails kumar2 = users
        //         .username("kumar2")
        //         .password(passwordEncoder.encode("xyz789"))
        //         .roles("CARD-OWNER")
        //         .build();

        return new InMemoryUserDetailsManager(john,jane);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
