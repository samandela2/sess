package com.example.sess.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.sess.dao.*;
import com.example.sess.models.*;
import com.example.sess.services.TaskService;


@Service
public class TaskService {
    
    private TaskRepository taskRepository;
    private UserRepository userRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    public Task findTask(Long id, String ownerName) {
        User owner = userRepository.findByName(ownerName);
        
        return taskRepository.findByIdAndOwnerId(id, owner.getId());
    }

    public Page<Task> findByOwner(String name, PageRequest pageRequest){
        Long id = userRepository.findIdByName(name);        
        return taskRepository.findByOwnerId(id, pageRequest);
    }

    public boolean existsByIdAndOwner(Long requstId, String name){
        Long id = userRepository.findIdByName(name);
        return taskRepository.existsByIdAndOwnerId(requstId,id);
    }

    public Task saveTask(Task tasktoSave) {
        
        
        return taskRepository.save(tasktoSave);
        
    }

    public void deleteById(@PathVariable Long requestId){
        taskRepository.deleteById(requestId);
    }

 

}
