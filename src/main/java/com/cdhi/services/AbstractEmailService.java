package com.cdhi.services;

import com.cdhi.domain.User;
import com.cdhi.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Value("${confirm.account.url}")
    private String confirm_url;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendUserConfirmationEmail(User obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromUser(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromUser(User obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getEmail());
        sm.setFrom(sender);
        sm.setSubject("CDHI - Usuário Cadastrado");
        sm.setText(obj.toString());
        return sm;
    }

    protected String htmlFromTemplateUser(User obj) {
        Context context = new Context();
        context.setVariable("user", obj);
        context.setVariable("confirm_url", confirm_url);
        return templateEngine.process("email/userConfirmation", context);
    }

    @Override
    public void sendUserConfirmationHtmlEmail(User obj) {
        try {
            MimeMessage mm = prepareMimeMessageFromUser(obj);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendUserConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeMessageFromUser(User obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(obj.getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("CDHI - Usuário Cadastrado");
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplateUser(obj), true);
        return mimeMessage;
    }

    @Override
    public void sendNewPasswordEmail(User user, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(user, newPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(User user, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(user.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de Nova Senha");
        sm.setText("Nova Senha: " + newPass);
        return sm;
    }
}
