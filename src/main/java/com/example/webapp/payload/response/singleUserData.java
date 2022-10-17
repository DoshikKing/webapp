package com.example.webapp.payload.response;

import com.example.webapp.components.Role;
import lombok.Data;
import java.util.List;

@Data
public class singleUserData {
    private Long id;
    private String login;
    private String password;
    private singleClientData client;
    private List<Role> roleList;
}
