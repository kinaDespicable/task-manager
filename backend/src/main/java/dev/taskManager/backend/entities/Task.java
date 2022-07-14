package dev.taskManager.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task implements Serializable {
    private static final long serialVersionUID = 5578888083096508767L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_creator", nullable = false)
    private User taskCreator;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "deadline_date")
    private Instant deadlineDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToMany
    @JoinTable(name = "employee_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<User> employees = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "responsible_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "responsible_id"))
    private Set<User> responsibles = new LinkedHashSet<>();

}