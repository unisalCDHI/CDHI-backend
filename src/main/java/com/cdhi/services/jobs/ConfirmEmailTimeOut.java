package com.cdhi.services.jobs;

import com.cdhi.domain.User;
import com.cdhi.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConfirmEmailTimeOut {

    @Autowired
    UserRepository repo;

    public void checkout() {
        log.info("Running Job... -> Procurando usuários com o tempo de confirmação de conta expirado");
        int count = 0;
        List<Map<String, Integer>> keys = repo.findKeys();

        List<User> usersDisabled = repo.findByIdIn(keys.stream().map(key -> key.get("USER_ID")).collect(Collectors.toList()));
        List<User> timedOutUsers = new ArrayList<>();

        for (User user : usersDisabled) {
            if (!user.getEnabled() && (System.currentTimeMillis() - user.getCreated().getTime()) >= 86400000) { //'enabled = false' and past '24' hours
                timedOutUsers.add(user);
                count++;
            }
        }
        if (!timedOutUsers.isEmpty())
            repo.deleteAll(timedOutUsers);
        log.info("Success -> Foram encontrados e removidos: {} Usuários", count);
    }
}
