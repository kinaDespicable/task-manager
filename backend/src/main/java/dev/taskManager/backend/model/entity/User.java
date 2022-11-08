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
@Table(name = "users")
@DynamicUpdate
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 3997086601929815872L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "taskCreator")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Task> tasks = new LinkedHashSet<>();

    @ManyToMany
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinTable(name = "employee_tasks",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private Set<Task> worksOn = new LinkedHashSet<>();

    @ManyToMany
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinTable(name = "responsible_task",
            joinColumns = @JoinColumn(name = "responsible_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private Set<Task> responsibleFor = new LinkedHashSet<>();

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private final User user = new User();

        public Builder email(String email){
            user.email = email;
            return this;
        }

        public Builder firstName(String firstName){
            user.name = firstName;
            return this;
        }

        public Builder lastName(String lastName){
            user.surname = lastName;
            return this;
        }

        public Builder password(String password){
            user.password = password;
            return this;
        }

        public Builder role(Role role){
            user.role = role;
            return this;
        }
        public User build(){
            return user;
        }
    }


}