package com.tlu.khoacntt.repository;

import com.tlu.khoacntt.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, String> {

    List<Lecturer> findByDegree(String degree);

    //
    List<Lecturer> findByNameContainingIgnoreCase(String name);

   
    List<Lecturer> findByDepartmentId(Integer departmentId);

    Optional<Lecturer> findByEmail(String email);
    boolean existsByEmail(String email);
}