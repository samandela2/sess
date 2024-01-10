package com.example.sess.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.sess.models.Task;

import com.example.sess.dao.TaskRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private Task findTask(Long id, String ownerName) {
        return taskRepository.findByIdAndOwner(id, ownerName);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Task> findById(@PathVariable Long requestId, Principal principal) {

        Task task = findTask(requestId, principal.getName());
        if (task != null) {
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Task>> findAll(Pageable pageable, Principal principal) {
        Page<Task> page = taskRepository.findByOwner(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "time"))));
        return ResponseEntity.ok(page.getContent());
    }

    @PostMapping
    public ResponseEntity<Void> creatTask(@RequestBody Task newTaskRequest, UriComponentsBuilder ucb,
            Principal principal) {

        Task tasktoSave = new Task(null, newTaskRequest.getTime(), principal.getName());
        Task savedTask = taskRepository.save(tasktoSave);

        URI locationOfNewTask = ucb.path("tasks/{id}")
                .buildAndExpand(savedTask.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewTask).build();
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<Void> updateTask(@PathVariable Long requestId, @RequestBody Task taskToUpdate,
            Principal principal) {
        Task task = findTask(requestId, principal.getName());
        if (task != null) {
            Task updateTask = new Task(task.getId(), taskToUpdate.getTime(), principal.getName());
            taskRepository.save(updateTask);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long requestId, Principal principal) {
        if (taskRepository.existsByIdAndOwner(requestId, principal.getName())) {
            taskRepository.deleteById(requestId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
