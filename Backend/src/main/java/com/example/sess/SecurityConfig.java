package com.example.sess;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http.authorizeHttpRequests(requests -> requests
        //         .requestMatchers("/tasks/**")
        //         .hasRole("USER"))
        //         .csrf(csrf -> csrf.disable())
        //         .httpBasic();
        // return http.build();

        // http.authorizeHttpRequests(requests -> requests
        //         .requestMatchers("/tasks/**")
        //         .hasAnyRole("USER","ADMIN"))
        //         .csrf(csrf -> csrf.disable())
        //         .httpBasic();
        // return http.build();

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/home/**").hasAnyRole("ADMIN", "USER")  // Access to home page for both roles
                .requestMatchers("/admin/**").hasRole("ADMIN")  // Admin-specific endpoints
                .requestMatchers("/tasks/**").hasAnyRole("ADMIN", "USER")  // Task endpoints accessible by both roles
                .anyRequest().authenticated())  // Any other request must be authenticated
                .csrf(csrf -> csrf.disable())
                .httpBasic(withDefaults());
        return http.build();
    
    }

    // @Bean
    // public UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
    //     User.UserBuilder users = User.builder();
    //     UserDetails john = users
    //             .username("john")
    //             .password(passwordEncoder.encode("pwd123"))
    //             .roles("USER")
    //             .build();
    //     UserDetails jane = users
    //             .username("jane")
    //             .password(passwordEncoder.encode("abc456"))
    //             .roles("USER")
    //             .build();
    //     UserDetails kyle = users
    //             .username("kyle")
    //             .password(passwordEncoder.encode("admin"))
    //             .roles("ADMIN")
    //             .build();

    //     return new InMemoryUserDetailsManager(john,jane,kyle);
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
