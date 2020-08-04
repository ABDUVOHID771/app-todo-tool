package com.example.todotool.controller;

import com.example.todotool.dao.domain.Project;
import com.example.todotool.dao.domain.User;
import com.example.todotool.dao.domain.UserPrincipal;
import com.example.todotool.service.ProjectService;
import com.example.todotool.service.UserService;
import com.google.common.base.Strings;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = {"http://localhost:3000","http://192.168.99.100:4200"})
@RestController
@RequestMapping("/rest/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createNewProject(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody Project project, BindingResult bindingResult) {

        ResponseEntity<?> errors = getErrors(bindingResult);
        if (errors != null) {
            return errors;
        }

        return new ResponseEntity<>(projectService.create(project, userPrincipal.getUsername()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ResponseEntity<>(projectService.getAll(userPrincipal.getUsername()), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            projectService.delete(uuid, userPrincipal.getUsername());
            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getProject(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID uuid) throws NotFoundException {
        return new ResponseEntity<>(projectService.getProject(uuid, userPrincipal.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody Project input, BindingResult result, @PathVariable UUID uuid) {


        if (input.getUuid() != null && !input.getUuid().equals(uuid)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).build();
        }

        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) {
            return errors;
        }

        try {
            input.setUuid(uuid);
            return ResponseEntity
                    .ok().body(projectService.update(input, userPrincipal.getUsername()));
        } catch (Exception e) {
            return ResponseEntity
                    .notFound().build();
        }

    }

    private ResponseEntity<?> getErrors(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
