package com.example.demo.Services.emailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to,String text,String subject){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("esportsemail.noreply@gmail.com");
        message.setTo(to);
        message.setText(text);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail sent...");
    }

    /*@Override
    public Response sendEmail(Email email) {
        Response response = new Response();
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getTo());
            message.setText(email.getText());
            message.setSubject(email.getSubject());

            response.setCode(0);
            response.setMessage("Email sent");
        } catch (Exception e) {
            response.setCode(1);
            response.setMessage("Email could not be delivered" + e.getMessage());
        }
        return response;
    }*/
}
