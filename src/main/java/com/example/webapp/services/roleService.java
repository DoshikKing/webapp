package com.example.webapp.services;

import com.example.webapp.components.Role;
import com.example.webapp.repos.roleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Component
@Transactional
public class roleService{

    roleRepo roleRepo;

    @Autowired
    public roleService(roleRepo roleRepo){
        this.roleRepo = roleRepo;
    }

    public void addRole(Role role) throws Exception {
        if(findByRole(role.getRole()) != null){
            throw new Exception("Exist");
        }
        roleRepo.save(role);
    }

    public Role findById(Long id) {
        return roleRepo.getById(id);
    }

    public Role findByRole(String role) {
        return roleRepo.findByRole(role);
    }

    public List<Role> findAll(){
        return roleRepo.findAll();
    }

    public void updateRole(Role role) throws Exception {
        addRole(role);
    }

    public void deleteRole(Role role) throws Exception {
        if(findById(role.getId()) == null){
            throw new Exception("Cant delete!");
        }
        roleRepo.delete(role);
    }
}
