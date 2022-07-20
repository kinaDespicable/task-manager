package dev.taskManager.backend.dto;

import dev.taskManager.backend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String name;
    private String surname;
    private String role;
    private String password;
    private String matchingPassword;

    @Data
    public static class ResponseDTO{
        private Long id;
        private String email;
        private String name;
        private String surname;
        private String role;
    }

    public static ResponseDTO toResponseDTO(User entity){
        ResponseDTO dto = new ResponseDTO();

        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole().getRoleName());

        return dto;
    }

    public static User toEntity(UserDTO dto){
        User user = new User();

        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());

        return user;
    }
}
