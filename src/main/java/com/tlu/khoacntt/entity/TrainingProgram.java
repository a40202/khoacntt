package com.tlu.khoacntt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "training_programs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "career_orientation", columnDefinition = "TEXT")
    private String careerOrientation;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(length = 100)
    private String duration;

    @Column(length = 100)
    private String level;

    // Quan hệ với Major (thay thế majorId thuần)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    // Quan hệ với danh sách Courses
    @OneToMany(mappedBy = "trainingProgram", fetch = FetchType.LAZY)
    @OrderBy("id ASC")
    private List<Course> courses;
}
