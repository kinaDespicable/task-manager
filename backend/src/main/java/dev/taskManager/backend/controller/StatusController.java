package dev.taskManager.backend.controller;

import dev.taskManager.backend.model.request.status.NewStatusRequest;
import dev.taskManager.backend.model.request.status.UpdateStatusRequest;
import dev.taskManager.backend.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;

    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody NewStatusRequest statusRequest){
        return new ResponseEntity<>(statusService.create(statusRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return new ResponseEntity<>(statusService.fetchById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getByName(@RequestParam("status") String statusName){
        return new ResponseEntity<>(statusService.fetchByName(statusName), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam(value = "p",required = false) Optional<Integer> p,
                                    @RequestParam(value = "s",required = false) Optional<Integer> s){
        int page = p.orElse(0);
        int size = s.orElse(5);
        return new ResponseEntity<>(statusService.fetchAll(page, size), HttpStatus.OK);
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateStatusRequest statusRequest){
        return new ResponseEntity<>(statusService.update(id,statusRequest),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return new ResponseEntity<>(statusService.delete(id), HttpStatus.OK);
    }

}
