package com.example.sess.models;
import org.springframework.data.annotation.Id;


public record Task(@Id Long id, String time, String owner) {
    
}
