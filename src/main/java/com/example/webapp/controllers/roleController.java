package com.example.webapp.controllers;

import com.example.webapp.components.Role;
import com.example.webapp.payload.request.createNewRole;
import com.example.webapp.payload.request.singleRoleById;
import com.example.webapp.payload.request.updateRole;
import com.example.webapp.payload.response.singleRoleData;
import com.example.webapp.services.roleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/role", produces="application/json")
public class roleController {

    roleService roleService;

    @Autowired
    public roleController(roleService roleService){
        this.roleService = roleService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getRoleById(){
        List<Role> roles = roleService.findAll();
        List<singleRoleData> singleRoleDataList = new ArrayList<>();
        for (Role role:roles) {
            singleRoleData singleRoleData = new singleRoleData();
            singleRoleData.setId(role.getId());
            singleRoleData.setRole(role.getRole());
            singleRoleDataList.add(singleRoleData);
        }
        return new ResponseEntity<>(singleRoleDataList, HttpStatus.OK);
    }

    @GetMapping("/getOne")
    public ResponseEntity<?> getRoleById(@RequestBody singleRoleById singleRoleById){
        Role role = roleService.findById(singleRoleById.getId());
        singleRoleData singleRoleData = new singleRoleData();
        singleRoleData.setId(role.getId());
        singleRoleData.setRole(role.getRole());

        return new ResponseEntity<>(singleRoleData, HttpStatus.OK);
    }

    @PostMapping("/createOne")
    public ResponseEntity<?> createNewClient(@RequestBody createNewRole createNewRole) {
        Role role = new Role();
        role.setRole(createNewRole.getRole());
        try{
            roleService.addRole(role);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/updateOne")
    public ResponseEntity<?> createNewClient(@RequestBody updateRole updateRole) throws Exception {
        Role role = roleService.findById(updateRole.getId());
        role.setId(updateRole.getId());
        role.setRole(updateRole.getRole());
        roleService.updateRole(role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteOne")
    public ResponseEntity<?> deleteClientById(@RequestBody singleRoleById singleRoleById) {
        Role role = roleService.findById(singleRoleById.getId());
        try{
            roleService.deleteRole(role);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
