package dev.taskManager.backend.controllers;

import dev.taskManager.backend.dto.UserDTO;
import dev.taskManager.backend.entities.User;
import dev.taskManager.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO requestBody){
        return new ResponseEntity<>(
                UserDTO.toResponseDTO(userService.register(requestBody)),
                HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAll(),HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        return new ResponseEntity<>(UserDTO.toResponseDTO(userService.fetchOne(id)),HttpStatus.OK);
    }


    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(UserDTO.toResponseDTO(userService.fetchByEmail(email)),HttpStatus.OK);
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO requestBody){
        return new ResponseEntity<>(UserDTO.toResponseDTO(userService.updateUser(id, requestBody)),HttpStatus.OK);
    }


    @DeleteMapping("/user/{id}")
    public Boolean deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
    //Todo:[Login endpoint]

}
