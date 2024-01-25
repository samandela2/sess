package com.example.sess.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.core.userdetails.User; // Spring Security User
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import org.springframework.stereotype.Service;

// import com.example.sess.dao.TaskRepository;
// import com.example.sess.dao.UserRepository;

// @Service
// public class UserService {

//     private TaskRepository taskRepository;
//     private UserRepository userRepository;

//     public UserService(UserRepository userRepository) {
//         this.userRepository = userRepository;
//     }

//     public Long getUserId(String name){

//         return userRepository.findIdByName(name);
//     }
// }


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sess.dao.UserRepository;
import com.example.sess.models.User;
import com.fasterxml.jackson.annotation.OptBoolean;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    // private final UserRepository userRepository;
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // public UserService(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    @Transactional
    public User createUser(String username, String rawPassword, String role) {
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        newUser.setRole(role);
        // Add other default settings or validations if needed

        System.out.println(newUser.getUserName() + " is Saving");
        return userRepository.save(newUser);
    }

    @Transactional
    public User updateUser(Long userId, String newUsername, String newRawPassword, String newRole) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUserName(newUsername);
        existingUser.setPassword(passwordEncoder.encode(newRawPassword));
        existingUser.setRole(newRole);
        // Implement other changes or business logic as needed

        return userRepository.save(existingUser);
    }

    
    public Long getUserId(String name) throws UsernameNotFoundException {

        logger.info("getUserId: "+ name + "runs");
        Optional<User> userOpt = userRepository.findByUserName(name);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            logger.info("getUserId: "+ name + "get: "+ user.getId());
            return user.getId();
        } else {
            // Handle the case where user is not found
            throw new UsernameNotFoundException("User not found with username: " + name);
        }
        
    }
    

    // public User getUserByUserName(String name){
    // return userRepository.findByUserName(name);
    // }


    

    public boolean isAdmin(String UserName) {
        return userRepository.findByUserName(UserName)
                .map(user -> user.getRole())
                .map(String::toUpperCase) // assuming role is stored as uppercase
                .filter(role -> role.equals("ADMIN"))
                .isPresent();
    }

    }

    


