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
@Table(name = "responsible_task")
public class ResponsibleTask implements Serializable {
    @Serial
    private static final long serialVersionUID = -9052784326846061568L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private ResponsibleTaskId id;

}