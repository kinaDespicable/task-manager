package dev.taskManager.backend.controllers;

import dev.taskManager.backend.entities.Status;
import dev.taskManager.backend.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatusController {

    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {

        this.statusService = statusService;
    }

    @PostMapping("/status")
    public ResponseEntity<?> createStatus(@RequestBody Status status){
        return new ResponseEntity<>(statusService.createStatus(status), HttpStatus.CREATED);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> getStatusById(@PathVariable Integer id){
        return new ResponseEntity<>(statusService.fetchById(id),HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatusByStatusName(@RequestParam("name") String statusName){
        return new ResponseEntity<>(statusService.fetchByStatusName(statusName),HttpStatus.OK);
    }

    @GetMapping("/statuses")
    public ResponseEntity<?> getAllStatuses(){
        return new ResponseEntity<>(statusService.fetchAll(),HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody Status requestBody){
        return new ResponseEntity<>(statusService.updateStatus(id, requestBody),HttpStatus.OK);
    }

    @DeleteMapping("/status/{id}")
    public Boolean deleteStatus(@PathVariable Integer id){
        return statusService.deleteStatus(id);
    }
}
