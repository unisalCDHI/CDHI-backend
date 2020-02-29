package com.cdhi.services;

import com.cdhi.domain.User;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendUserConfirmationEmail(User obj);

    void sendEmail(SimpleMailMessage msg);

    void sendUserConfirmationHtmlEmail(User obj);

    void sendHtmlEmail(MimeMessage msg);
}
