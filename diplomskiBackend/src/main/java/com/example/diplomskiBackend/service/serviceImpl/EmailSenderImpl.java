package com.example.diplomskiBackend.service.serviceImpl;

import com.example.diplomskiBackend.service.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderImpl implements EmailSender {

    @Value("${app.email}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String email, String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setText(email, true);
            helper.setFrom(from);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println(e);
            throw new IllegalStateException("Failed to send email");
        }
    }


}
