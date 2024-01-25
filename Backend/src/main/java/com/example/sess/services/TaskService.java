package com.example.sess.services;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Pageable;
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

//     public Task findTask(Long id){
//         Optional<Task> optionalTask = taskRepository.findById(id);
//         if (optionalTask.isPresent()) {
//             Task task = optionalTask.get();
//             return task;
//         } else {
//             return null;
//         }
//     }

    
//     public Page<Task> findAll(PageRequest pageRequest,Long id){
        
//         Page<Task> page = TaskRepository.findByOwner(id,
//         PageRequest.of(
//                 pageable.getPageNumber(),
//                 pageable.getPageSize(),
//                 pageable.getSortOr(Sort.by(Sort.Direction.ASC, "startTime"))));



//         return taskRepository.findAll(pageRequest);
//     }


//     public Page<Task> findByOwner(String name, PageRequest pageRequest){
//         Long ownerId = userRepository.findIdByUsername(name);        
//         return taskRepository.findByOwnerId(ownerId, pageRequest);
//     }

//     public boolean existsByIdAndOwner(Long requstId, String name){
//         Long id = userRepository.findIdByUsername(name);
//         return taskRepository.existsByIdAndOwnerId(requstId,id);
//     }

//    //TODO: 
//     public Task updateTask(Long id, Task tasktoUpdate, String name) {

//         // Task task = taskRepository.findByIdAndOwnerId(id, 0)
//         // Task task = taskService.findOwnedTask(requestId, principal.getName());
//         // if (task != null) {
//         //     Task updateTask = new Task(task.getId(), taskToUpdate.getStartTime(),taskToUpdate.getEndTime(), userService.getUserId(principal.getName()),taskToUpdate.getClientId(),taskToUpdate.getLocation(), taskToUpdate.getType(), taskToUpdate.getDescription());
//         //     taskService.updateTask(updateTask,principal);
//         //     return ResponseEntity.noContent().build();
//         // }


//         return taskRepository.save(tasktoUpdate);
        
//     }

//     public void deleteById(@PathVariable Long requestId){
//         taskRepository.deleteById(requestId);
//     }

 

}

