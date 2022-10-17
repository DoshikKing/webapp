package com.example.webapp.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class updateUser {
    private Long id;
    private String login;
    private String password;
    private Long client_id;
    private List<Long> role_ids;
}
