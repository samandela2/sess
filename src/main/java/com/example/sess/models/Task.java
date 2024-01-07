package com.example.sess.models;
import org.springframework.data.annotation.Id;


public record Task(@Id long id, String time, String owner) {
    
}
