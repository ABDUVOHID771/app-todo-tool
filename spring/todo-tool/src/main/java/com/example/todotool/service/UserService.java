package com.example.todotool.service;

import com.example.todotool.dao.domain.User;
import com.example.todotool.dao.repository.UserRepository;
import com.example.todotool.exception.UsernameException;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User update(User updating) throws NotFoundException {

        User user = userRepository.findById(updating.getId()).orElseThrow(() -> new NotFoundException("Not Found !"));

        return userRepository.save(user);

    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setConfirmPassword("");
            user.setAuthorities("USER");
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UsernameException(user.getUsername());
        }
    }

    public User getByUuid(UUID uuid) throws NotFoundException {
        return userRepository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Not found"));
    }

}
