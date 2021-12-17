package com.example.demo.Services.emailService;

import com.example.demo.domain.Match;
import com.example.demo.domain.Speler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailToSpelers(Speler to, Match match){

        DateFormat dfdatum = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dftijd = new SimpleDateFormat("HH:mm");


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("esportsemail.noreply@gmail.com");
        message.setTo(to.getUser().getEmail());
        message.setText(
                "Beste " + to.getUser().getUsername() + ",\n"
                + "\n"
                + "Match: " + match.getTeamBlue().getNaam() + " vs. " + match.getTeamRed().getNaam() + ".\n"
                + "datum: " + dfdatum.format(match.getDatumtijd()) + "\n"
                + "tijd: " + dftijd.format(match.getDatumtijd()) + "\n"
                + "\n"
                + "mvg,\n"
                + "GIP4 Team"
        );
        message.setSubject("Match: " + match.getTeamBlue().getNaam() + " vs. " + match.getTeamRed().getNaam());

        mailSender.send(message);
        System.out.println("Mail sent to " + to.getUser().getUsername() + ". email: " + to.getUser().getEmail() +"...");
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
