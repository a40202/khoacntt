package com.tlu.khoacntt.repository;

import com.tlu.khoacntt.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByTrainingProgram_IdOrderByIdAsc(Integer programId);
}
