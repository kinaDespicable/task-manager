package dev.taskManager.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
@DynamicUpdate
public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = -259726241120548010L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;


    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "deadline_date")
    private Instant deadlineDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;


    @JsonProperty("task_creator")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "task_creator", nullable = false)
    private User taskCreator;

    @ManyToMany(mappedBy = "responsibleFor", fetch = FetchType.LAZY)
    private Set<User> responsible = new HashSet<>();

    @ManyToMany(mappedBy = "worksOn", fetch = FetchType.LAZY)
    private Set<User> employee = new HashSet<>();


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private final Task task = new Task();

        public Builder title(String title){
            task.title = title;
            return this;
        }

        public Builder description(String description){
            task.description = description;
            return this;
        }

        public Builder creator(User user){
            task.taskCreator = user;
            return this;
        }

        public Builder creationDate(Instant createdAt){
            task.creationDate = createdAt;
            return this;
        }

        public Builder responsible(Set<User> responsible){
            task.responsible = responsible;
            return this;
        }

        public Builder employee(Set<User> employee){
            task.employee = employee;
            return this;
        }

        public Builder deadline(Instant deadline){
            task.deadlineDate = deadline;
            return this;
        }

        public Builder status(Status status){
            task.status = status;
            return this;
        }

        public Task build(){
            return task;
        }
    }


}