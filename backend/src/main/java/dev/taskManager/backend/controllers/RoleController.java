package dev.taskManager.backend.controllers;

import dev.taskManager.backend.entities.Role;
import dev.taskManager.backend.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/role")
    public ResponseEntity<?> createRole(@RequestBody Role role){
        Role response = roleService.createRole(role);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/role/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Integer id){
        return new ResponseEntity<>(roleService.fetchRoleById(id),HttpStatus.OK);
    }
    @GetMapping("/role")
    public ResponseEntity<?> getRoleByName(@RequestParam String roleName){
        return new ResponseEntity<>(roleService.fetchRoleByRoleName(roleName),HttpStatus.OK);
    }
    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles(){
        return new ResponseEntity<>(roleService.fetchAll(),HttpStatus.OK);
    }
    @PutMapping("/role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @RequestBody Role role){
        return new ResponseEntity<Role>(roleService.updateRole(id, role),HttpStatus.OK);
    }
    @DeleteMapping("/role/{id}")
    public Boolean deleteRole(@PathVariable Integer id){
        return roleService.deleteRole(id);
    }
}
