package dev.taskManager.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ResponsibleTaskId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1701454421525363866L;
    @Column(name = "responsible_id", nullable = false)
    private Long responsibleId;

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ResponsibleTaskId entity = (ResponsibleTaskId) o;
        return Objects.equals(this.responsibleId, entity.responsibleId) &&
                Objects.equals(this.taskId, entity.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responsibleId, taskId);
    }

}