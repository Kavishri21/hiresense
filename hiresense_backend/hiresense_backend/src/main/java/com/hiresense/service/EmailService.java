package com.hiresense.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendShortlistEmail(
            String toEmail,
            String candidateName,
            String jobTitle
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Shortlisted for " + jobTitle);
        message.setText(
                "Hello " + candidateName + ",\n\n" +
                "Congratulations! You have been shortlisted for the role of " +
                jobTitle + ".\n\n" +
                "Our recruitment team will contact you shortly.\n\n" +
                "Regards,\nHireSense Team"
        );

        mailSender.send(message);
    }
}
