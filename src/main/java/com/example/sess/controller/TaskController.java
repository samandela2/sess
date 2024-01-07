package com.example.sess.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.sess.models.Task;
import com.example.sess.dao.TaskRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskRepository taskRepository;
    
}
