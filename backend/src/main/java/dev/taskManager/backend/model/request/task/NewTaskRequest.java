package dev.taskManager.backend.model.request.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.taskManager.backend.model.entity.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public record NewTaskRequest(
        @Size(min = 3, message = "Title should have at least 3 characters long.")
        @JsonProperty("title")
        @NotBlank(message = "Title field cannot be blank.")
        String title,

        @JsonProperty("description")
        @NotBlank(message = "Description field cannot be blank.")
        String description,

        @JsonProperty("deadline")
        Instant deadline,

        @JsonProperty("responsible")
        Set<String> responsible,

        @JsonProperty("employee")
        Set<String> employee
) {
}
