package com.example.webapp.controllers;

import com.example.webapp.components.Client;
import com.example.webapp.components.User;
import com.example.webapp.payload.request.createNewClient;
import com.example.webapp.payload.request.singleClientById;
import com.example.webapp.payload.request.updateClient;
import com.example.webapp.payload.response.singleClientData;
import com.example.webapp.services.clientService;
import com.example.webapp.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/client", produces="application/json")
public class clientController {

    clientService clientService;
    userService userService;

    @Autowired
    public clientController(clientService clientService, userService userService){
        this.clientService = clientService;
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getClientById(){
        List<Client> clients = clientService.findAll();
        List<singleClientData> singleClientDataList = new ArrayList<>();
        for (Client client:clients) {
            singleClientData singleClientData = new singleClientData();
            singleClientData.setId(client.getId());
            singleClientData.setName(client.getName());
            singleClientData.setSurname(client.getSurname());
            singleClientDataList.add(singleClientData);
        }
        return new ResponseEntity<>(singleClientDataList ,HttpStatus.OK);
    }

    @GetMapping("/getOne")
    public ResponseEntity<?> getClientById(@RequestBody singleClientById singleClientById){

        Client client = clientService.findById(singleClientById.getId());

        singleClientData singleClientData = new singleClientData();
        singleClientData.setId(client.getId());
        singleClientData.setName(client.getName());
        singleClientData.setSurname(client.getSurname());

        return new ResponseEntity<>(singleClientData ,HttpStatus.OK);
    }

    @PostMapping("/createOne")
    public ResponseEntity<?> createNewClient(@RequestBody createNewClient createNewClient) {
        Client client = new Client();
        client.setName(createNewClient.getName());
        client.setSurname(createNewClient.getSurname());
        List<User> users = new ArrayList<>();
        try {
            for (int i = 0; i < createNewClient.getUser_ids().size(); i++) {
                User user = userService.findById(createNewClient.getUser_ids().get(i));
                users.add(user);
            }
            client.setRecordList(users);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        try {
            clientService.addClient(client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/updateOne")
    public ResponseEntity<?> updateClientById(@RequestBody updateClient updateClient) throws Exception {
        Client client = clientService.findById(updateClient.getId());
        client.setId(updateClient.getId());
        client.setName(updateClient.getName());
        client.setSurname(updateClient.getSurname());
        List<User> users = new ArrayList<>();
        try {
            for (int i = 0; i < updateClient.getUser_ids().size(); i++) {
                User user = userService.findById(updateClient.getUser_ids().get(i));
                users.add(user);
            }
            client.setRecordList(users);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        clientService.updateClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteOne")
    public ResponseEntity<?> deleteClientById(@RequestBody singleClientById singleClientById) {
        Client client = clientService.findById(singleClientById.getId());
        try {
            clientService.deleteClient(client);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
