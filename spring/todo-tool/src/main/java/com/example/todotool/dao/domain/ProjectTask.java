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
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProjectTask {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, unique = true)
    private String projectSequence;

    @NotBlank(message = "Please include a project summary")
    private String summary;

    private String acceptanceCriteria;

    private String status;

    private Integer priority = 0;

    @NotNull(message = "Due date is required")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dueDate;

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd")
    @CreatedDate
    private Date createdDate;

    @Column
    @JsonFormat(pattern = "yyyy-mm-dd")
    @LastModifiedDate
    private Date updatedDate;

    @Column(updatable = false)
    private String projectIdentifier;

}
