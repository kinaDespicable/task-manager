package dev.taskManager.backend.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSummary {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

}
