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
@Table(name = "responsible_task")
public class ResponsibleTask implements Serializable {
    private static final long serialVersionUID = -9072060510804985007L;
    @EmbeddedId
    private ResponsibleTaskId id;

    //TODO [JPA Buddy] generate columns from DB
}