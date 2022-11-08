package dev.taskManager.backend.model.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record NewUserRequest(
        @JsonProperty("email")
        @Email(message = "Invalid email format.")
        @NotBlank(message = "Email field cannot be blank.")
        String email,

        @JsonProperty("first_name")
        @NotBlank(message = "First name field cannot be blank.")
        String firstName,

        @JsonProperty("last_name")
        @NotBlank(message = "Last name field cannot be blank.")
        String lastName,

        @JsonProperty("password")
        @NotBlank(message = "Password field cannot be blank.")
        String password,

        @JsonProperty("matching_password")
        @NotBlank(message = "Matching password field cannot be blank.")
        String matchingPassword,

        @JsonProperty("role")
        @NotBlank(message = "Role field cannot be blank.")
        String role
) {
}
