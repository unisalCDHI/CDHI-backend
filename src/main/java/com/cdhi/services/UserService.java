package com.cdhi.services;

import com.cdhi.domain.User;
import com.cdhi.repositories.UserRepository;
import com.cdhi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    public User findOne(Integer id) {
        return repo.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException("Theres no user with id: " + id);
        });
    }

    public List<User> findAll() {
        return repo.findAll();
    }


    public User create(User user) {
        try {
            user.setId(null);
            return repo.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
