package com.example.webapp.services;

import com.example.webapp.components.Client;
import com.example.webapp.repos.clientRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Component
@Transactional
public class clientService{

    clientRepo clientRepo;

    @Autowired
    public clientService(clientRepo clientRepo){
        this.clientRepo = clientRepo;
    }

    public void addClient(Client client) throws Exception {
        if(findByClientName(client.getName()) != null){
            throw new Exception("Exist");
        }
        clientRepo.save(client);
    }

    public Client findByClientName(String name) {
        return clientRepo.findByName(name);
    }

    public Client findById(Long id) {
        return clientRepo.getById(id);
    }

    public List<Client> findAll(){
        return clientRepo.findAll();
    }

    public void updateClient(Client client) throws Exception {
         addClient(client);
    }

    public void deleteClient(Client client) throws Exception {
        if (findById(client.getId()) == null){
            throw new Exception("Cant delete!");
        }
        clientRepo.delete(client);
    }
}
