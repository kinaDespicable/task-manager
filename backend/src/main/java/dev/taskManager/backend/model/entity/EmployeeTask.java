package dev.taskManager.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employee_tasks")
public class EmployeeTask implements Serializable {
    @Serial
    private static final long serialVersionUID = 4638816066690876810L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private EmployeeTaskId id;

}