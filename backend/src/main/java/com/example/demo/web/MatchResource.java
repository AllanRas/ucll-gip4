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
        //sendEmail();
        return matchService.addMatch(createMatchDTO);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<CreateMatchDTO> getAllMatches(){
        return matchService.getAllMatches();
    }


    private void sendEmail(){
        emailService.sendSimpleEmail("esportsemail.noreply@gmail.com","This is auto email","Test");
    }


    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/matchresult")
    public MatchStatsDTO matchResultsInvoren(@RequestBody MatchStatsDTO matchStatsDTO){
        return matchService.setResults(matchStatsDTO);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/matchstats/allteam")
    public void matchStatsVanAlleTeam(){
        matchService.getAllMatchStats();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/matchstats/{id}")
    public void matchStatsVan1Team(@PathVariable("id")long id){
        matchService.getById(id);
    }

    /*
     * SPELER
     */

    @PreAuthorize("hasRole('SPELER')")
    @GetMapping("/matchhistory")
    public void allPreviousMatches(){
        matchService.getAllMatchesHistory();
    }

    //TODO Team statistieken bekijken
    @PreAuthorize("hasRole('SPELER')")
    @GetMapping("/matchstats/eigen")
    public void eigenTeamStats(long id){
        matchService.getById(id);
    }

    //TODO Persoonlijke matchhistoriek
    @PreAuthorize("hasRole('SPELER')")
    @GetMapping("/matchhistory/{id}")
    public void eigenPreviousMatches(@PathVariable("id")long id){
        matchService.getById(id);
    }
}
