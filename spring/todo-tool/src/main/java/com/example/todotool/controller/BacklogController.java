package com.example.todotool.controller;

import com.example.todotool.dao.domain.Project;
import com.example.todotool.dao.domain.ProjectTask;
import com.example.todotool.dao.domain.UserPrincipal;
import com.example.todotool.service.ProjectTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000","http://192.168.99.100:4200"})
@RestController
@RequestMapping("/rest/backlog")
public class BacklogController {

    private final ProjectTaskService projectTaskService;

    public BacklogController(ProjectTaskService projectTaskService) {
        this.projectTaskService = projectTaskService;
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<List<ProjectTask>> getProjectBacklog(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable String projectIdentifier) {
        return new ResponseEntity<>(projectTaskService.findBackLogByIdentifier(projectIdentifier, userPrincipal.getUsername()), HttpStatus.OK);
    }

    @PostMapping("/{projectIdentifier}")
    public ResponseEntity<?> createNewProjectTask(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult, @PathVariable String projectIdentifier) {

        ResponseEntity<?> errors = getErrors(bindingResult);
        if (errors != null) {
            return errors;
        }

        ProjectTask projectTask_ = projectTaskService.addProjectTask(projectIdentifier, projectTask, userPrincipal.getUsername());

        return new ResponseEntity<>(projectTask_, HttpStatus.CREATED);
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

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectSequence(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable String backlog_id, @PathVariable String pt_id) {
        return new ResponseEntity<>(projectTaskService.findPTByProjectSequence(backlog_id, pt_id, userPrincipal.getUsername()), HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable String backlog_id, @PathVariable String pt_id, @Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult) {

        ResponseEntity<?> errors = getErrors(bindingResult);
        if (errors != null) {
            return errors;
        }

        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id, userPrincipal.getUsername());

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);

    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable String backlog_id, @PathVariable String pt_id) {
        try {
            projectTaskService.deletePTByProjectSequence(backlog_id, pt_id, userPrincipal.getUsername());
            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

}
