package dev.taskManager.backend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Set;

@Getter
public class TaskSummary {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private String status;

    @JsonProperty("task_creator")
    private UserSummary taskCreator;

    @JsonProperty("responsible")
    private Set<UserSummary> responsible;

    @JsonProperty("employee")
    private Set<UserSummary> employees;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private final TaskSummary summary = new TaskSummary();

        public Builder id(Long id){
            summary.id = id;
            return this;
        }

        public Builder title(String title){
            summary.title = title;
            return this;
        }

        public Builder description(String description){
            summary.description = description;
            return this;
        }

        public Builder status(String status){
            summary.status = status;
            return this;
        }

        public Builder taskCreator(UserSummary taskCreator){
            summary.taskCreator = taskCreator;
            return this;
        }

        public Builder responsible(Set<UserSummary> responsible){
            summary.responsible = responsible;
            return this;
        }

        public Builder employee(Set<UserSummary> employee){
            summary.employees = employee;
            return this;
        }
        public TaskSummary build(){
            return summary;
        }

    }
}
