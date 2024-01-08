package com.example.sess.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.sess.models.Task;


public interface TaskRepository extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    
    Task findByIdAndOwner(long id, String owner);
    Page<Task> findByOwner(String name, PageRequest pageRequest);
}
