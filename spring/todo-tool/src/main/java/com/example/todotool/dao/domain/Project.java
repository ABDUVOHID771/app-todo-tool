package com.example.todotool.dao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Project {

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    private Backlog backlog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private String projectLeader;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(unique = true, nullable = false, updatable = false, length = 16)
    private UUID uuid;

    @NotBlank(message = "Project name should not be empty.")
    private String projectName;

    @NotBlank(message = "Project identifier should not be empty.")
    @Size(min = 4, max = 5, message = "Please use 4 to 5 characters")
    @Column(updatable = false, unique = true)
    private String projectIdentifier;

    @NotBlank(message = "Project description should not be empty.")
    private String description;

    @NotNull(message = "Started date is required")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date startedDate;

    @NotNull(message = "Targeted date is required")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date endedDate;

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @CreatedDate
    private Date createdDate;

    @Column
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @LastModifiedDate
    private Date updatedDate;

    @PrePersist
    public void generateUUID() {
        this.uuid = UUID.fromString(UUID.randomUUID().toString().substring(2, 36));
    }

}
