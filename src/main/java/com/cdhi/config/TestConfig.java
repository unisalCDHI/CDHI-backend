package com.cdhi.config;

import com.cdhi.services.DBService;
import com.cdhi.services.EmailService;
import com.cdhi.services.MockEmailService;
import com.cdhi.services.jobs.ConfirmEmailTimeOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.util.Date;

@Configuration
@Profile("test")
@EnableScheduling
public class TestConfig {

    private static final int interval = 300000;

    @Autowired
    private DBService dbService;

    @Autowired
    private ConfirmEmailTimeOut confirmEmailTimeOut;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }

    @Scheduled(fixedRate = interval)
    public void scheduleFixedRateTask() {
        confirmEmailTimeOut.checkout(interval);
    }
}
