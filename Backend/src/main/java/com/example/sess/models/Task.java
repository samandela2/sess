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
import java.time.format.DateTimeFormatter;
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
    @Column (name = "taskId")
    private Long id;

    @Column(name = "startTime", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime startTime;

    @Column(name = "endTime", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime endTime;


    @Column(name = "owner", nullable =  false)
    private long ownerId;

    @Column(name = "client")
    private Long clientId;

    @Column(name = "location")
    private String location;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;


    public Task(Long id, LocalDateTime startTime, LocalDateTime endTime, long ownerId, Long clientId, String location, String type, String description) {
        if(id != null)  this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ownerId = ownerId;
        if(clientId != null)  this.clientId = clientId;
        this.location = location;
        this.type = type;
        this.description = description;
    }


    public Task(Long id, String startTime, String endTime, long ownerId, Long clientId, String location, String type, String description) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        if(id != null)  this.id = id;
        this.startTime = LocalDateTime.parse(startTime,formatter);
        this.endTime = LocalDateTime.parse(endTime,formatter);
        this.ownerId = ownerId;
        if(clientId != null)  this.clientId = clientId;
        this.location = location;
        this.type = type;
        this.description = description;
    }


    protected Task(){}


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getClientId() {
        if(this.clientId == null)  return null;
        return this.clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
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
        return Objects.equals(ownerId, task.ownerId) &&
               Objects.equals(id, task.id) &&
               Objects.equals(startTime, task.startTime) &&
               Objects.equals(endTime, task.endTime) &&
               Objects.equals(clientId, task.clientId) && 
               Objects.equals(location, task.location) &&
               Objects.equals(type, task.type) &&
               Objects.equals(description, task.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime, ownerId, clientId, location, type, description);
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
