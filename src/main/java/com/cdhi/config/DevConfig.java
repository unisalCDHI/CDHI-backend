package com.cdhi.config;

import com.cdhi.services.DBService;
import com.cdhi.services.EmailService;
import com.cdhi.services.SmtpEmailService;
import com.cdhi.services.jobs.ConfirmEmailTimeOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;

@Configuration
@Profile("dev")
@EnableScheduling
public class DevConfig {

    private static final int interval = 300000;

    @Autowired
    private DBService dbService;

    @Autowired
    private ConfirmEmailTimeOut confirmEmailTimeOut;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        if (!"create".equals(strategy))
            return false;
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }

    @Scheduled(fixedRate = interval)
    public void scheduleFixedRateTask() {
        confirmEmailTimeOut.checkout(interval);
    }
}
