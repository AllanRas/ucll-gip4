package com.example.demo.web;

import com.example.demo.Services.MatchService;
import com.example.demo.Services.SpelerService;
import com.example.demo.Services.emailService.EmailSenderService;
import com.example.demo.config.UserPrincipal;
import com.example.demo.domain.Match;
import com.example.demo.domain.Speler;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.MatchDTO;
import com.example.demo.dto.SpelerDTO;
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
@RequestMapping("/matches")
@CrossOrigin("http://localhost:3000/")
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
        return matchService.addMatch(createMatchDTO);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<MatchDTO> getAllMatches(){
        return matchService.getAllMatches();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/matchresult")
    public MatchDTO matchResultsInvoren(@RequestBody MatchDTO matchDTO){
        return matchService.setResults(matchDTO);
    }

    @PreAuthorize("hasAnyRole('MANAGER','SPELER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable("id")long id){
        // id = TeamId
        MatchDTO matchDTO = matchService.getById(id);

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isSpelerRole = userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SPELER"));
        if(isSpelerRole){
            SpelerDTO speler = spelerService.getByIdAndUser(userPrincipal);
            List<MatchDTO> matchDTOList = matchService.getMatchesBySpelerId(speler.getId());
            for (MatchDTO mdto: matchDTOList) {
                if(mdto.getId() == matchDTO.getId()){
                    return ResponseEntity.ok().body(matchDTO);
                }
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(matchDTO);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/matchstats/allteam")
    public List<MatchDTO> matchStatsVanAlleTeam(){
        return matchService.getAllMatchStats();
    }


    @PreAuthorize("hasAnyRole('MANAGER','SPELER')")
    @GetMapping(value = "/matchstats/{id}")
    public List<MatchDTO> matchStatsVan1Team(@PathVariable("id")long id){
        // id = TeamId
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return matchService.getByTeamId(id, userPrincipal);
    }

    /*
     * SPELER
     */

    @PreAuthorize("hasRole('SPELER')")
    @GetMapping("/matchstats/eigen")
    public MatchDTO eigenTeamStats(long id){
        return matchService.getById(id);
    }


    @PreAuthorize("hasRole('SPELER')")
    @GetMapping("/matchhistory/speler")
    public List<MatchDTO> eigenPreviousMatches(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SpelerDTO speler = spelerService.getByIdAndUser(userPrincipal);
        return matchService.getMatchesBySpelerId(speler.getId());
    }
}
