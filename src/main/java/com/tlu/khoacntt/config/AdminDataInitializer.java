package com.tlu.khoacntt.config;

import com.tlu.khoacntt.entity.Admin;
import com.tlu.khoacntt.entity.Role;
import com.tlu.khoacntt.repository.AdminRepository;
import com.tlu.khoacntt.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Khi chưa có admin nào trong DB: tạo role ADMIN + tài khoản mặc định (mật khẩu BCrypt).
 */
@Component
@Order(1)
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (adminRepository.count() > 0) {
            return;
        }

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("ADMIN");
                    return roleRepository.save(r);
                });

        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("Quản trị viên");
        admin.setRole(adminRole);
        adminRepository.save(admin);
    }
}
