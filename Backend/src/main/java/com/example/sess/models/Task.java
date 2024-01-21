// package com.example.sess.models;
// import org.springframework.data.annotation.Id;

// public record Task(@Id Long id, String time, String owner) {

// }

package com.example.sess.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "task")
public class Task {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "task_id")
    private Long id;

    @Column(name = "start_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime start_time;

    @Column(name = "end_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime end_time;


    @Column(name = "owner", nullable =  false)
    private long owner_id;

    @Column(name = "client")
    private Long client_id;

    @Column(name = "location")
    private String location;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;


    public Task(Long id, LocalDateTime start_time, LocalDateTime end_time, long owner_id, Long client_id, String location, String type, String description) {
        this.id = id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.owner_id = owner_id;
        if(client_id != null)  this.client_id = client_id;
        this.location = location;
        this.type = type;
        this.description = description;
    }


    protected Task(){}


    public void setId(Long id) {
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public LocalDateTime getStart_time() {
        return this.start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public long getOwner_id() {
        return this.owner_id;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public Long getClient_id() {
        if(this.client_id == null)  return null;
        return this.client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return Objects.equals(owner_id, task.owner_id) &&
               Objects.equals(id, task.id) &&
               Objects.equals(start_time, task.start_time) &&
               Objects.equals(end_time, task.end_time) &&
               Objects.equals(client_id, task.client_id) && 
               Objects.equals(location, task.location) &&
               Objects.equals(type, task.type) &&
               Objects.equals(description, task.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, start_time, end_time, owner_id, client_id, location, type, description);
    }
}
    // public Task(Long id, String time, String owner) {
    //     this.id = id;
    //     this.time = time;
    //     this.owner = owner;
    // }

    // protected Task(){}

    // // Getters and setters
    // public Long getId() {
    //     return id;
    // }

    // public String getTime() {
    //     return time;
    // }

    // public String getOwner() {
    //     return owner;
    // }

    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj)
    //         return true;
    //     if (obj == null || getClass() != obj.getClass())
    //         return false;
    //     Task task = (Task) obj;
    //     return Objects.equals(id, task.id) &&
    //             Objects.equals(owner, task.owner);
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(id, time, owner);
    // }

    
// }
