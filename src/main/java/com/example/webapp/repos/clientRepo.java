package com.example.webapp.repos;

import com.example.webapp.components.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface clientRepo extends JpaRepository<Client, Long> {
    Client findByName(String client_name);
}
