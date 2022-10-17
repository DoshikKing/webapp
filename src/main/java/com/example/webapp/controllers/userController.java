package com.example.webapp.controllers;

import com.example.webapp.components.Role;
import com.example.webapp.components.User;
import com.example.webapp.payload.request.createNewUser;
import com.example.webapp.payload.request.singleUserById;
import com.example.webapp.payload.request.updateUser;
import com.example.webapp.payload.response.singleClientData;
import com.example.webapp.payload.response.singleUserData;
import com.example.webapp.services.clientService;
import com.example.webapp.services.roleService;
import com.example.webapp.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/user", produces="application/json")
public class userController {

    userService userService;
    clientService clientService;
    com.example.webapp.services.roleService roleService;

    @Autowired
    public userController(userService userService, clientService clientService, roleService roleService){
        this.userService = userService;
        this.clientService = clientService;
        this.roleService = roleService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<User> users = userService.findAll();
        List<singleUserData> singleUserDataList = new ArrayList<>();
        for (User user:users) {
            singleUserData singleUserData = new singleUserData();
            singleUserData.setId(user.getId());
            singleUserData.setLogin(user.getLogin());
            singleUserData.setPassword(user.getPassword());
            singleClientData singleClientData = new singleClientData();
            singleClientData.setId(user.getClient().getId());
            singleClientData.setName(user.getClient().getName());
            singleClientData.setSurname(user.getClient().getSurname());
            singleUserData.setClient(singleClientData);
            singleUserData.setRoleList(user.getRoleList());
            singleUserDataList.add(singleUserData);
        }
        return new ResponseEntity<>(singleUserDataList, HttpStatus.OK);
    }

    @GetMapping("/getOne")
    public ResponseEntity<?> getUserById(@RequestBody singleUserById singleUserById){
        User user = userService.findById(singleUserById.getId());
        singleUserData singleUserData = new singleUserData();
        singleUserData.setId(user.getId());
        singleUserData.setLogin(user.getLogin());
        singleUserData.setPassword(user.getPassword());
        singleClientData singleClientData = new singleClientData();
        singleClientData.setId(user.getClient().getId());
        singleClientData.setName(user.getClient().getName());
        singleClientData.setSurname(user.getClient().getSurname());
        singleUserData.setClient(singleClientData);
        singleUserData.setRoleList(user.getRoleList());

        return new ResponseEntity<>(singleUserData, HttpStatus.OK);
    }

    @PostMapping("/createOne")
    public ResponseEntity<?> createNewClient(@RequestBody createNewUser createNewUser) {
        User user = new User();
        user.setLogin(createNewUser.getLogin());
        user.setPassword(createNewUser.getPassword());
        user.setClient(clientService.findById(createNewUser.getClient_id()));
        List<Role> roles = new ArrayList<>();
        try {
            for (int i = 0; i < createNewUser.getRole_ids().size(); i++) {
                Role role = roleService.findById(createNewUser.getRole_ids().get(i));
                roles.add(role);
            }
            user.setRoleList(roles);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        try{
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/updateOne")
    public ResponseEntity<?> createNewClient(@RequestBody updateUser updateUser) throws Exception {
        User user = userService.findById(updateUser.getId());
        user.setId(updateUser.getId());
        user.setLogin(updateUser.getLogin());
        user.setPassword(updateUser.getPassword());
        user.setClient(clientService.findById(updateUser.getClient_id()));
        List<Role> roles = new ArrayList<>();
        try {
            for (int i = 0; i < updateUser.getRole_ids().size(); i++) {
                Role role = roleService.findById(updateUser.getRole_ids().get(i));
                roles.add(role);
            }
            user.setRoleList(roles);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteOne")
    public ResponseEntity<?> deleteClientById(@RequestBody singleUserById singleUserById) {
        User user = userService.findById(singleUserById.getId());
        try{
            userService.deleteUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
