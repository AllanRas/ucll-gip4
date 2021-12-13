package com.example.demo.web;

import com.example.demo.Services.MatchService;
import com.example.demo.Services.emailService.EmailSenderService;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.MatchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchResource {

    private final MatchService matchService;

    @Autowired
    private EmailSenderService emailService;

    public MatchResource(MatchService matchService) {
        this.matchService = matchService;
    }

    /*
     * MANAGER
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public CreateMatchDTO addMatch(@RequestBody CreateMatchDTO createMatchDTO) {
        sendEmail();
        return matchService.addMatch(createMatchDTO);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<CreateMatchDTO> getAllMatches(){
        return matchService.getAllMatches();
    }

    //TODO mail sturen
    private void sendEmail(){
        emailService.sendSimpleEmail("esportsemail.noreply@gmail.com","This is auto email","Test");
    }

    //TODO Matchresults invoeren

    //TODO Statistieken van teams bekijken

    //TODO Statistieken van 1 team bekijken


    /*
     * SPELER
     */
    //TODO Team matchhistoriek bekijken

    //TODO Team statistieken bekijken

    //TODO Persoonlijke matchhistoriek
}
