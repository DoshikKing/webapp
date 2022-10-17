package com.example.webapp.repos;

import com.example.webapp.components.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepo extends JpaRepository<User, Long> {
    User findByLogin(String login);
    User findByClientId(Long id);
}
