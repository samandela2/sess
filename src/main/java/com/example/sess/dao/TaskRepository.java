package com.example.sess.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.scheduling.config.Task;

public interface TaskRepository extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    
}
