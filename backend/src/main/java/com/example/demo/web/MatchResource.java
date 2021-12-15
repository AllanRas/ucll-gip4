package com.example.demo.web;

import com.example.demo.Services.MatchService;
import com.example.demo.Services.emailService.EmailSenderService;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.MatchDTO;
import com.example.demo.dto.match.MatchStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateMatchDTO addMatch(@RequestBody CreateMatchDTO createMatchDTO) {
        sendEmail();
        return matchService.addMatch(createMatchDTO);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<CreateMatchDTO> getAllMatches(){
        return matchService.getAllMatches();
    }

    //stuurt een mail naar (de gegeven mail)
    private void sendEmail(){
        emailService.sendSimpleEmail("esportsemail.noreply@gmail.com","This is auto email","Test");
    }

    //moeten nog aanpassen
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("matchresult")
    public MatchStatsDTO matchResultsInvoren(@RequestBody MatchStatsDTO matchStatsDTO){
        return matchService.setResults(matchStatsDTO);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/matchstats/allteam")
    public List<MatchStatsDTO> matchStatsVanAlleTeam(){
        return matchService.getAllMatchStats();
    }


    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/matchstats/{id}")
    public MatchDTO matchStatsVan1Team(@PathVariable("id")long id){
        return matchService.getById(id);
    }

    /*
     * SPELER
     */

    @PreAuthorize("hasRole('SPELER')")
    @GetMapping("/matchhistory")
    public List<MatchDTO> allPreviousMatches(){
        return matchService.getAllMatchesHistory();
    }

    //TODO Team statistieken bekijken
    @PreAuthorize("hasRole('SPELER')")
    @GetMapping("/matchstats/eigen")
    public MatchDTO eigenTeamStats(long id){
        return matchService.getById(id);
    }

    //TODO Persoonlijke matchhistoriek
    @PreAuthorize("hasRole('SPELER')")
    @GetMapping("/matchhistory/{id}")
    public MatchDTO eigenPreviousMatches(@PathVariable("id")long id){
        return matchService.getById(id);
    }
}
