package com.example.webapp.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class createNewClient {
    private String name;
    private String surname;
    private List<Long> user_ids;
}
