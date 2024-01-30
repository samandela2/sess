package com.example.sess;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.sess.config.RsaKeyConfigProperties;
import com.example.sess.dao.UserRepository;
import com.example.sess.models.User;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@EntityScan("com.example.sess.models")
public class SessApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessApplication.class, args);
	}

	//  @Bean
    // public CommandLineRunner initializeUser(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    //     return args -> {

    //             User user = new User();
    //             user.setUserName("john");
    //             user.setEmail("john@gmail.com");
    //             user.setPassword(passwordEncoder.encode("pwd123"));
	// 			user.setRole("USER");

	// 			User user1 = new User();
    //             user.setUserName("jane");
    //             user.setEmail("jane@gmail.com");
    //             user.setPassword(passwordEncoder.encode("abc456"));
	// 			user.setRole("USER");

	// 			User user2 = new User();
    //             user.setUserName("kyle");
    //             user.setEmail("example@gmail.com");
    //             user.setPassword(passwordEncoder.encode("admin"));
	// 			user.setRole("ADMIN");


    //             // Save the user to the database
    //             userRepository.save(user);
    //             userRepository.save(user1);
    //             userRepository.save(user2);


    //     };
    // }

}
