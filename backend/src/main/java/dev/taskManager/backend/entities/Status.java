package dev.taskManager.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "status")
public class Status implements Serializable {
    private static final long serialVersionUID = -4030137171720169936L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id", nullable = false)
    private Integer id;

    @Column(name = "status_name", nullable = false, length = 50)
    private String statusName;

    @OneToMany(mappedBy = "status")
    private Set<Task> tasks = new LinkedHashSet<>();

}