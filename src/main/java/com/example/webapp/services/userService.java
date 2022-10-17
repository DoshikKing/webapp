package com.example.webapp.services;

import com.example.webapp.components.User;
import com.example.webapp.repos.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Component
@Transactional
public class userService {

    userRepo userRepo;

    @Autowired
    public userService(userRepo userRepo){
        this.userRepo = userRepo;
    }

    public void addUser(User user) throws Exception {
        if(findUser(user.getLogin()) != null){
            throw new Exception("Exist");
        }
        userRepo.save(user);
    }

    public User findById(Long id) {
        return userRepo.getById(id);
    }

    public User findUser(String login) {
        return userRepo.findByLogin(login);
    }

    public User findByClientId(Long id) {
        return userRepo.findByClientId(id);
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public void updateUser(User user) throws Exception {
        addUser(user);
    }

    public void deleteUser(User user) throws Exception {
        if(findById(user.getId()) == null){
            throw new Exception("cant delete");
        }
        userRepo.delete(user);
    }
}
