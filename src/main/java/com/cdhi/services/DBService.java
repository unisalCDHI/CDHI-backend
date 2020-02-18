package com.cdhi.services;

import com.cdhi.controllers.exceptions.Error;
import com.cdhi.domain.User;
import com.cdhi.repositories.UserRepository;
import com.cdhi.services.exceptions.ObjectAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Arrays;

@Service
public class DBService {
    @Autowired
    UserRepository userRepository;

    public void instantiateTestDatabase() throws ParseException {
        userRepository.saveAll(Arrays.asList(
                new User("Jorge", "jorgesilva@gmail.com"),
                new User("Caio", "caiosilveiranunes@piririm.com")
        ));
    }
}
