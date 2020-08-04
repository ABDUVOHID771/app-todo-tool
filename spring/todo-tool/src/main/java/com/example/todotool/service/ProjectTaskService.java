package com.example.todotool.service;

import com.example.todotool.dao.domain.Backlog;
import com.example.todotool.dao.domain.Project;
import com.example.todotool.dao.domain.ProjectTask;
import com.example.todotool.dao.repository.ProjectTaskRepository;
import com.example.todotool.exception.ResourceNotFoundException;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    private final BacklogService backlogService;
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectService projectService;

    public ProjectTaskService(BacklogService backlogService, ProjectTaskRepository projectTaskRepository, ProjectService projectService) {
        this.backlogService = backlogService;
        this.projectTaskRepository = projectTaskRepository;
        this.projectService = projectService;
    }

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

        try {
            Backlog backlog = projectService.getByProjectIdentifier(projectIdentifier, username).getBacklog();
            projectTask.setBacklog(backlog);
            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            if (Strings.isNullOrEmpty(projectTask.getStatus())) {
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);
        } catch (Exception exception) {
            throw new ResourceNotFoundException(projectIdentifier);
        }
    }

    public List<ProjectTask> findBackLogByIdentifier(String identifier, String username) {
        try {
            Project project = projectService.getByProjectIdentifier(identifier, username);

            if (project == null) {
                throw new ResourceNotFoundException(identifier);
            }

            return projectTaskRepository.findByProjectIdentifierOrderByPriority(identifier);
        } catch (Exception e) {
            throw new ResourceNotFoundException(identifier);
        }
    }


    public ProjectTask findPTByProjectSequence(String backlog_id, String sequence, String username) {

        Backlog backlog = projectService.getByProjectIdentifier(backlog_id, username).getBacklog();
        if (backlog == null) {
            throw new ResourceNotFoundException(backlog_id);
        }
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence);
        if (projectTask == null) {
            throw new ResourceNotFoundException(sequence);
        }
        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ResourceNotFoundException(sequence);
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask projectTask, String backlog_id, String pt_id, String username) {
        ProjectTask projectTask_ = findPTByProjectSequence(backlog_id, pt_id, username);
        System.out.println(projectTask_.getId());
        projectTask_.setStatus(projectTask.getStatus());
        projectTask_.setPriority(projectTask.getPriority());
        projectTask_.setAcceptanceCriteria(projectTask.getAcceptanceCriteria());
        projectTask_.setDueDate(projectTask.getDueDate());
        projectTask_.setSummary(projectTask.getSummary());

        return projectTaskRepository.save(projectTask_);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTaskRepository.delete(projectTask);
    }


}
