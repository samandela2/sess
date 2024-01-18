package com.example.sess.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.sess.dao.TaskRepository;
import com.example.sess.models.Task;
import com.example.sess.services.TaskService;


@Service
public class TaskService {
    
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    public Task findTask(Long id, String ownerName) {
        return taskRepository.findByIdAndOwner(id, ownerName);
    }


    public Page<Task> findByOwner(String name, PageRequest pageRequest){
        return taskRepository.findByOwner(name, pageRequest);
    }

    public boolean existsByIdAndOwner(Long requstId, String name){
        return taskRepository.existsByIdAndOwner(requstId,name);
    }

    public Task saveTask(Task tasktoSave) {
        
        
        return taskRepository.save(tasktoSave);
        
    }

    public void deleteById(@PathVariable Long requestId){
        taskRepository.deleteById(requestId);
    }

 

}
