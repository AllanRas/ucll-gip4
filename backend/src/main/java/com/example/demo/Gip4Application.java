package com.example.demo;

import com.example.demo.Services.emailService.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Gip4Application {

   /* @Autowired
    private EmailSenderService emailSenderService;*/

    public static void main(String[] args) {
        SpringApplication.run(Gip4Application.class, args);
    }

    /*@EventListener(ApplicationReadyEvent.class)
    public void triggerMail(){
        emailSenderService.sendSimpleEmail("esportsemail.noreply@gmail.com","This is auto email","test");
    }*/

    @Bean
    public BCryptPasswordEncoder BCryptPasswordEncoder(){ return new BCryptPasswordEncoder(); }
}
