package com.tlu.khoacntt.repository;

import com.tlu.khoacntt.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    Optional<Admin> findByUsername(String username);

    @Query("SELECT a FROM Admin a LEFT JOIN FETCH a.role WHERE LOWER(a.username) = LOWER(:username)")
    Optional<Admin> findByUsernameWithRole(@Param("username") String username);

    Optional<Admin> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    
}