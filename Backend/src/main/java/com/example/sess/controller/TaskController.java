package com.example.sess.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.sess.models.*;
import com.example.sess.services.TaskService;
import com.example.sess.services.UserService;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{requestId}")
    public ResponseEntity<Task> findById(@PathVariable Long requestId, Principal principal) {
        Task task = taskService.findOwnedTaskById(requestId, principal.getName());
        System.out.println("task ID " + requestId + " name: "+principal.getName() + " ---" +task);
        if (task != null) {
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }
    

//     @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
//     @GetMapping
//     public ResponseEntity<List<Task>> findAll(Pageable pageable, Principal principal) {

//         Page<Task> page = taskService.findAll(pageable, principal.getName());
        
//         return ResponseEntity.ok(page.getContent());
//     }

//     @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
//     @PutMapping("/{requestId}")
//     public ResponseEntity<Void> updateTask(@PathVariable Long requestId, @RequestBody Task taskToUpdate,
//             Principal principal) {
//         // Task task = taskService.findOwnedTask(requestId, principal.getName());
//         // if (task != null) {
//         //     Task updateTask = new Task(task.getId(), taskToUpdate.getStartTime(),taskToUpdate.getEndTime(), userService.getUserId(principal.getName()),taskToUpdate.getClientId(),taskToUpdate.getLocation(), taskToUpdate.getType(), taskToUpdate.getDescription());
//         //     taskService.updateTask(updateTask,principal);
//         //     return ResponseEntity.noContent().build();
//         // }

//         // return ResponseEntity.notFound().build();
//         Task task = taskService.updateTask(requestId, taskToUpdate, principal.getName());     
//         return task==null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
//     }

    

    //admin
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @GetMapping("/admin/{requestId}")
    // public ResponseEntity<Task> findByIdAdmin(@PathVariable Long requestId, Principal principal) {
    //     Task task = taskService.findTask(requestId);
    //     if (task != null) {
    //         return ResponseEntity.ok(task);
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @GetMapping("/admin")
    // public ResponseEntity<List<Task>> findAllAdmin(Pageable pageable, Principal principal) {
    //     Page<Task> page = taskService.findAll(
    //     PageRequest.of(
    //             pageable.getPageNumber(),
    //             pageable.getPageSize(),
    //             pageable.getSortOr(Sort.by(Sort.Direction.ASC, "startTime"))));
    //     return ResponseEntity.ok(page.getContent());
    // }

    // //TODO:
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @PostMapping
    // public ResponseEntity<Void> creatTask(@RequestBody Task newTaskRequest, UriComponentsBuilder ucb,
    //         Principal principal) {
    //     long userId = userService.getUserId(principal.getName());
    //     Task taskToSave = new Task(null, newTaskRequest.getStartTime(), newTaskRequest.getEndTime(), userId, newTaskRequest.getClientId(), newTaskRequest.getLocation(), newTaskRequest.getType(), newTaskRequest.getDescription());
    //     Task savedTask = taskService.updateTask(userId, taskToSave,principal.getName());
    //     URI locationOfNewTask = ucb.path("tasks/{id}")
    //             .buildAndExpand(savedTask.getId())
    //             .toUri();
    //     return ResponseEntity.created(locationOfNewTask).build();
    // }

    // @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    // @DeleteMapping("/{requestId}")
    // public ResponseEntity<Void> deleteTask(@PathVariable Long requestId, Principal principal) {
    //     if (taskService.existsByIdAndOwner(requestId, principal.getName())) {
    //         taskService.deleteById(requestId);
    //         return ResponseEntity.noContent().build();
    //     }
    //     return ResponseEntity.notFound().build();
    // }




}
