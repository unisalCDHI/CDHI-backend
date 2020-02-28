package com.cdhi.services;

import com.cdhi.domain.User;
import com.cdhi.dtos.UserDTO;
import com.cdhi.repositories.BoardRepository;
import com.cdhi.repositories.UserRepository;
import com.cdhi.services.exceptions.ObjectAlreadyExistsException;
import com.cdhi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    @Autowired
    BoardRepository boardRepository;

    public User findOne(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("There's no user with id: " + id));
    }

    public List<UserDTO> findAll(String name) {
        return repo.findDistinctByNameContainingIgnoreCase(name).stream().map(UserDTO::new).collect(Collectors.toList());
    }


    public User create(UserDTO userDTO) {
        userDTO.setId(null);
        if (repo.findByEmail(userDTO.getEmail()) != null)
            throw new ObjectAlreadyExistsException("This Email is already in use");
        else
            return repo.save(toObject(userDTO));
    }

    public User toObject(UserDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user);
    }

    public User save(UserDTO userDTO, Integer userId) {
        User userToUpdate = findOne(userId);
        userToUpdate
                .setName(userDTO.getName());
        repo.save(userToUpdate);
        return findOne(userId);
    }

    public void delete(Integer userId) {
        repo.deleteById(userId);
    }

    public Page<User> findAllByPage(String name, Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        return repo.findDistinctByNameContainingIgnoreCase(name, pageRequest);
    }
}
