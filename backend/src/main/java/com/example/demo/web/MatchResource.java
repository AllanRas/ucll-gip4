package com.example.demo.web;

import com.example.demo.Services.MatchService;
import com.example.demo.Services.SpelerService;
import com.example.demo.Services.emailService.EmailSenderService;
import com.example.demo.config.UserPrincipal;
import com.example.demo.domain.User;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.MatchDTO;
import com.example.demo.dto.SpelerDTO;
import com.example.demo.dto.SpelerMatchDTO;
import com.example.demo.dto.match.MatchStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchResource {

    private final MatchService matchService;

    @Autowired
    private SpelerService spelerService;

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
    @PutMapping(value = "/{id}/matchresult", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchDTO> matchResultsInvoren(@PathVariable("id")long id, @RequestBody MatchDTO matchStatsDTO){
        MatchDTO updatedmatch = matchService.setResults(id, matchStatsDTO);

        return new ResponseEntity<MatchDTO>(updatedmatch, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/matchstats/allteam", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<MatchDTO> matchStatsVanAlleTeam(){
        return matchService.getAllMatchStats();
    }


    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/matchstats/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MatchDTO matchStatsVan1Team(@PathVariable("id")long id){
        return matchService.getById(id);
    }

    /*
     * SPELER
     */
    //TODO nog moeten fixen


    @PreAuthorize("hasRole('SPELER')")
    @GetMapping("/matchhistory")
    public List<SpelerMatchDTO> allPreviousMatches(){
        return matchService.allMatchesVanSpeler();
    }


    @PreAuthorize("hasRole('SPELER')")
    @GetMapping("/matchstats/eigen")
    public MatchDTO eigenTeamStats(long id){
        return matchService.getById(id);
    }

   /* //TODO Persoonlijke matchhistoriek
    @PreAuthorize("hasRole('SPELER')")
    @GetMapping("/matchhistory/{id}")
    public MatchDTO eigenPreviousMatches(@PathVariable("id")long id){
        return matchService.getById(id);
    }*/
}
