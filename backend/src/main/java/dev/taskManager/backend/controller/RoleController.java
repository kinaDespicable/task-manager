package dev.taskManager.backend.controller;

import dev.taskManager.backend.model.request.role.NewRoleRequest;
import dev.taskManager.backend.model.request.role.UpdateRoleRequest;
import dev.taskManager.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody NewRoleRequest roleRequest){
        return new ResponseEntity<>(roleService.create(roleRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return new ResponseEntity<>(roleService.fetchById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getByName(@RequestParam("role") String roleName){
        return new ResponseEntity<>(roleService.fetchByName(roleName), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam(value = "p", required = false) Optional<Integer> p,
                                    @RequestParam(value = "s", required = false) Optional<Integer> s,
                                    @RequestParam(value = "sortBy", required = false) Optional<String> sortBy){
        int page = p.orElse(0);
        int size = s.orElse(5);

        return new ResponseEntity<>(roleService.fetchAll(page,size,sortBy), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateRoleRequest roleRequest){
        return new ResponseEntity<>(roleService.update(id,roleRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return new ResponseEntity<>(roleService.delete(id), HttpStatus.OK);
    }

}
