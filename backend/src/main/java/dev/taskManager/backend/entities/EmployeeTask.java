package dev.taskManager.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employee_tasks")
public class EmployeeTask implements Serializable {
    private static final long serialVersionUID = -357823635366456280L;
    @EmbeddedId
    private EmployeeTaskId id;

    //TODO [JPA Buddy] generate columns from DB
}