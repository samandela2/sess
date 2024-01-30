package com.example.sess.services;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.sess.dao.*;
import com.example.sess.models.*;
import com.example.sess.services.UserService;
import com.example.sess.services.TaskService;
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

     
    // public TaskService(TaskRepository taskRepository, UserService userDetailsService) {
    //     this.taskRepository = taskRepository;
    //     this.userService = userDetailsService;
    // }
    

    public Task findOwnedTaskById(Long id, String ownerName) {

        Long userid = userService.getUserId(ownerName);
        System.out.println("userid is "+ userid);
        return taskRepository.findByIdAndOwnerId(id, userid);
    }

    public Task findTask(Long id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            return task;
        } else {
            return null;
        }
    }

    
    public Page<Task> findAll(Pageable pageable,String username){   
        Long userid = userService.getUserId(username);
        Page<Task> page = taskRepository.findByOwnerId(userid,
        PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "startTime"))
                ));
        return page;
    }

    public Page<Task> findAll(Pageable pageable){   
        Page<Task> page = taskRepository.findAll(
            PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "startTime"))
                ));
        return page;
    }

    public URI createATask(Task newTaskRequest, UriComponentsBuilder ucb,
    String userName){

        if(isTaskPresent(newTaskRequest))   return null;
        Task taskToSave = new Task(null, newTaskRequest.getStartTime(), newTaskRequest.getEndTime(), newTaskRequest.getOwnerId(), newTaskRequest.getClientId(), newTaskRequest.getLocation(), newTaskRequest.getType(), newTaskRequest.getDescription());
        Task savedTask = taskRepository.save(taskToSave);
        URI locationOfNewTask = ucb.path("tasks/{id}")
                .buildAndExpand(savedTask.getId())
                .toUri();
        return locationOfNewTask;
    }


    public boolean isTaskPresent(Task task){
        return taskRepository.existsByStartTimeAndEndTimeAndOwnerIdAndClientIdAndLocationAndTypeAndDescription(task.getStartTime(),task.getEndTime(),task.getOwnerId(),task.getClientId(),task.getLocation(),task.getType(),task.getDescription());
    }

   //TODO: 
    public Task updateTask(Long id, Task datatoUpdate, String name) {

        Optional<Task> taskBeingUpdated = taskRepository.findById(id);
        
        if (taskBeingUpdated.isEmpty()) {
            return null;
        }
        datatoUpdate.setId(taskBeingUpdated.get().getId());
        taskRepository.save(datatoUpdate);

        return datatoUpdate;
        
    }

    public void deleteById(@PathVariable Long requestId){
        taskRepository.deleteById(requestId);
    }

    public boolean existsByIdAndOwner(Long requestId, String name) {
        return taskRepository.existsByIdAndOwnerId(requestId, userService.getUserId(name));
    }

 

}

