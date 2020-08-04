package com.example.todotool.dao.repository;

import com.example.todotool.dao.domain.Project;
import com.example.todotool.dao.domain.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String identifier);

    ProjectTask findByProjectSequence(String sequence);

}
