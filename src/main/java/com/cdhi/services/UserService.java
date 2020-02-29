package com.cdhi.services;

import com.cdhi.domain.User;
import com.cdhi.dtos.NewUserDTO;
import com.cdhi.dtos.UserDTO;
import com.cdhi.repositories.BoardRepository;
import com.cdhi.repositories.UserRepository;
import com.cdhi.services.exceptions.ObjectAlreadyExistsException;
import com.cdhi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BCryptPasswordEncoder CRYPTER;

    @Autowired
    private EmailService emailService;

    public User findOne(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("There's no user with id: " + id));
    }

    public List<UserDTO> findAll(String name) {
        return repo.findDistinctByNameContainingIgnoreCase(name).stream().map(UserDTO::new).collect(Collectors.toList());
    }


    public User create(NewUserDTO newUserDTO) {
        newUserDTO.setId(null);
        if (repo.findByEmail(newUserDTO.getEmail()) != null)
            throw new ObjectAlreadyExistsException("This Email is already in use");
        else {
            newUserDTO.setPassword(CRYPTER.encode(newUserDTO.getPassword()));
            User user = repo.save(toObject(newUserDTO));
            emailService.sendUserConfirmationHtmlEmail(findOne(user.getId()));
            return user;
        }
    }

    public User toObject(NewUserDTO newUserDTO) {
        return new User(newUserDTO.getName(), newUserDTO.getEmail(), newUserDTO.getPassword());
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

    public User save(String newPassword, Integer userId) {
        User userToUpdate = findOne(userId);
        userToUpdate.setPassword(CRYPTER.encode(newPassword));
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

    public User enable(Integer id, String _key) {
        List<String> key = new ArrayList<>();
        key.add(_key);

        User user = findOne(id);
        if (user.get_key().get(0).equals(key.get(0))) {
            user.setEnabled(true);
            repo.save(user);
            return findOne(id);
        }
        throw new ObjectNotFoundException("Key not found, check your email");
    }
}
