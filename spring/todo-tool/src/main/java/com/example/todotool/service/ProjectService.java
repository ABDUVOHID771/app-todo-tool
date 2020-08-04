package com.example.todotool.service;

import com.example.todotool.dao.domain.Backlog;
import com.example.todotool.dao.domain.Project;
import com.example.todotool.dao.domain.User;
import com.example.todotool.dao.repository.ProjectRepository;
import com.example.todotool.exception.DuplicateException;
import com.example.todotool.exception.ResourceNotFoundException;
import com.google.common.base.Strings;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final BacklogService backlogService;
    private final UserService userService;

    public ProjectService(ProjectRepository projectRepository, BacklogService backlogService, UserService userService) {
        this.projectRepository = projectRepository;
        this.backlogService = backlogService;
        this.userService = userService;
    }

    public List<Project> getAll(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public Project create(Project project, String username) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            User user = userService.getByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            Backlog backlog = new Backlog();
            project.setBacklog(backlog);
            backlog.setProject(project);
            backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new DuplicateException(project.getProjectIdentifier());
        }
    }

    @Transactional
    public void delete(UUID uuid, String username) {
        Project project = getProject(uuid, username);
        projectRepository.deleteByUuid(project.getUuid());
    }

    public Project getProject(UUID uuid, String username) {
        Project project = projectRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException(uuid));
        if (!project.getProjectLeader().equals(username)) {
            throw new ResourceNotFoundException(uuid);
        }
        return project;
    }

    public Project update(Project updating, String username) throws NotFoundException {

        Project project = getProject(updating.getUuid(), username);

        if (!Strings.isNullOrEmpty(project.getProjectName())) {
            project.setProjectName(updating.getProjectName());
        }
        if (!Strings.isNullOrEmpty(project.getDescription())) {
            project.setDescription(updating.getDescription());
        }
        if (updating.getStartedDate() != null) {
            project.setStartedDate(updating.getStartedDate());
        }
        if (updating.getEndedDate() != null) {
            project.setEndedDate(updating.getEndedDate());
        }
        return projectRepository.save(project);

    }

    public Project getByProjectIdentifier(String projectIdentifier, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
        if (!project.getProjectLeader().equals(username)) {
            throw new ResourceNotFoundException(projectIdentifier);
        }
        return project;
    }

}
