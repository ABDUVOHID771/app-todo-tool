package com.example.todotool.service;

import com.example.todotool.dao.domain.Backlog;
import com.example.todotool.dao.repository.BacklogRepository;
import org.springframework.stereotype.Service;

@Service
public class BacklogService {

    private final BacklogRepository backlogRepository;

    public BacklogService(BacklogRepository backlogRepository) {

        this.backlogRepository = backlogRepository;
    }

    public Backlog getByProjectIdentifier(String identifier) {
        return backlogRepository.findByProjectIdentifier(identifier);
    }

    public Backlog createBacklog(Backlog backlog) {
        return backlogRepository.save(backlog);
    }

}
