package com.example.sess.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.sess.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query method to find a user by name
    Optional<User> findByUserName(String userName);

    // @Query("SELECT u.id FROM User u WHERE u.userName = :userName")
    // Long findIdByUserName(String userName);

    

    

   
}
