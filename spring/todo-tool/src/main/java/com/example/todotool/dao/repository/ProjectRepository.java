package com.example.todotool.dao.repository;

import com.example.todotool.dao.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByUuid(UUID uuid);

    List<Project> findAllByProjectLeader(String username);

    void deleteByUuid(UUID uuid);

    Project findByProjectIdentifier(String projectIdentifier);

}
