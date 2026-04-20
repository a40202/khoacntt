package com.tlu.khoacntt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lecturers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NonNull
    private String id;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String avatar; 

    @Column(length = 100)
    private String degree; 

    @Column(length = 100)
    private String position;

    @Column(length = 255)
    private String specialization; 

    @Column(name = "department_id")
    private Integer departmentId; 

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(length = 20)
    private String phone; 

    @Column(name = "research_area", length = 500)
    private String researchArea; 

    @Column(columnDefinition = "TEXT")
    private String bio; 



}