// package com.example.sess.models;
// import org.springframework.data.annotation.Id;

// public record Task(@Id Long id, String time, String owner) {

// }

package com.example.sess.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "owner")
    private String owner;

    public Task(Long id, String time, String owner) {
        this.id = id;
        this.time = time;
        this.owner = owner;
    }

    protected Task(){}

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Task task = (Task) obj;
        return Objects.equals(id, task.id) &&
                Objects.equals(time, task.time) &&
                Objects.equals(owner, task.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, owner);
    }

}
