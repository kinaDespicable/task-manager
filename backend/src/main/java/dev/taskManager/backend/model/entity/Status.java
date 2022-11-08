package dev.taskManager.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "status")
@DynamicUpdate
public class Status implements Serializable {
    @Serial
    private static final long serialVersionUID = 8661366479637949849L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id", nullable = false)
    private Integer id;

    @JsonProperty("status_name")
    @Column(name = "status_name", nullable = false, length = 50)
    private String statusName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "status")
    private Set<Task> tasks = new LinkedHashSet<>();

    public Status(String statusName){
        this.statusName = statusName;
    }

}