package com.cdhi.services.jobs;

import com.cdhi.domain.User;
import com.cdhi.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConfirmEmailTimeOut {

    @Autowired
    UserRepository repo;

    private final static int accountExpiration = 86400000;

    public void checkout(int interval) {

        log.info("Rodando o Job cada " + interval + " ms... -> Procurando usuários com o tempo de confirmação de conta expirado");
        int count = 0;
        List<Map<String, Integer>> keys = repo.findKeys();

        List<User> usersDisabled = repo.findByIdIn(keys.stream().map(key -> key.get("USER_ENTITY_ID")).collect(Collectors.toList()));
        List<User> timedOutUsers = new ArrayList<>();

        for (User user : usersDisabled) {
            if (!user.getEnabled() &&
                    (Date.from(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("America/Sao_Paulo")).toInstant()).getTime() - user.getCreated().getTime()) >= accountExpiration) { //'enabled = false' and past '24' hours
                timedOutUsers.add(user);
                count++;
            }
        }
        if (!timedOutUsers.isEmpty())
            repo.deleteAll(timedOutUsers);
        log.info("Success -> Foram encontrados e removidos: {} Usuários", count);
    }
}
