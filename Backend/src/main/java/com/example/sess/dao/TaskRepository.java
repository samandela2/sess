package com.example.sess.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sess.models.Task;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    
//     Task findByIdAndOwner(long id, String owner);
//     Page<Task> findByOwner(String name, PageRequest pageRequest);
//     boolean existsByIdAndOwner(Long requstId, String name);

    Task findByIdAndOwnerId(Long id, long ownerId);
    // Task findByIdAndOwner_id(long id, Long owner_id);
    Page<Task> findByOwnerId(long ownerId, PageRequest pageRequest);
    boolean existsByIdAndOwnerId(Long requestId, long ownerId);

    
}
