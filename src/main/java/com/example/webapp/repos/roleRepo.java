package com.example.webapp.repos;

import com.example.webapp.components.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepo extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
