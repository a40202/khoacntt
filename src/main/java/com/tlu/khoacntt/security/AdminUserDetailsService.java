package com.tlu.khoacntt.security;

import com.tlu.khoacntt.entity.Admin;
import com.tlu.khoacntt.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;


@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String login = username != null ? username.trim() : "";
        Admin admin = adminRepository.findByUsernameWithRole(login)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + login));

        String roleForSpring = resolveRoleName(admin);
        String storedPassword = admin.getPassword() != null ? admin.getPassword().trim() : "";

        return User.builder()
                .username(admin.getUsername())
                .password(storedPassword)
                .roles(roleForSpring)
                .build();
    }

    private static String resolveRoleName(Admin admin) {
        if (admin.getRole() == null || admin.getRole().getName() == null || admin.getRole().getName().isBlank()) {
            return "ADMIN";
        }
        String rn = admin.getRole().getName().trim();
        if (rn.regionMatches(true, 0, "ROLE_", 0, 5)) {
            rn = rn.substring(5);
        }
        return rn.toUpperCase(Locale.ROOT);
    }
}
