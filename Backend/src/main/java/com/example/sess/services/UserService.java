package com.example.sess.services;

import org.springframework.stereotype.Service;

import com.example.sess.dao.TaskRepository;
import com.example.sess.dao.UserRepository;

@Service
public class UserService {
    
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getUserId(String name){
        return userRepository.findIdByName(name);
    }

    
    
}
