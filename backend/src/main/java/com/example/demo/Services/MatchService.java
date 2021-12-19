package com.example.demo.Services;

import com.example.demo.Converter.MatchConverter;
import com.example.demo.Converter.TeamConverter;

import com.example.demo.config.UserPrincipal;
import com.example.demo.dao.*;

import com.example.demo.Services.emailService.EmailSenderService;
import com.example.demo.dao.MatchRepository;
import com.example.demo.dao.SpelerMatchRepository;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.dao.TeamRepository;

import com.example.demo.domain.*;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.MatchDTO;
import com.example.demo.dto.SpelerMatchDTO;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final SpelerMatchRepository spelerMatchRepository;
    private final TeamRepository teamRepository;
    private final SpelerRepository spelerRepository;
    private final SpelerTeamRepository spelerTeamRepository;
    private final MatchConverter matchConverter;
    private final TeamConverter teamConverter;
    private final EmailSenderService emailSenderService;



    public MatchService(MatchRepository matchRepository, SpelerMatchRepository spelerMatchRepository, TeamRepository teamRepository, SpelerRepository spelerRepository, SpelerTeamRepository spelerTeamRepository, MatchConverter matchConverter, TeamConverter teamConverter, EmailSenderService emailSenderService) {
        this.matchRepository = matchRepository;
        this.spelerMatchRepository = spelerMatchRepository;
        this.teamRepository = teamRepository;
        this.spelerRepository = spelerRepository;
        this.spelerTeamRepository = spelerTeamRepository;
        this.matchConverter = matchConverter;
        this.teamConverter = teamConverter;
        this.emailSenderService = emailSenderService;
    }

    //Create Match
    public CreateMatchDTO addMatch(CreateMatchDTO matchDTO){
        Match match = new Match();

        Set<SpelerMatchDTO> spelers = matchDTO.getSpelers();

        match.setTeamBlue(teamRepository.getById(matchDTO.getTeamBlueId()));
        match.setTeamRed(teamRepository.getById(matchDTO.getTeamRedId()));
        match.setDatumtijd(matchDTO.getDatumtijd());

        Match newmatch = matchRepository.save(match);

        for (SpelerMatchDTO spelerMatchDTO: spelers) {
            Match matchspeler = matchRepository.findById(newmatch.getId()).orElseThrow();
            Speler speler = spelerRepository.findById(spelerMatchDTO.getSpelerid()).orElseThrow();

            SpelerMatch spelerMatch = new SpelerMatch.Builder()
                    .match(matchspeler)
                    .speler(speler)
                    .teamid(spelerMatchDTO.getTeamid())
                    .build();
            spelerMatchRepository.save(spelerMatch);
            emailSenderService.sendEmailToSpelers(speler, newmatch);
        }

        return matchConverter.matchToCreateMatchDTO(newmatch);
    }

    //get all matches
    public List<MatchDTO> getAllMatches(){
        return matchConverter.matchListToMatchDTO(matchRepository.findAll());
    }

    //match results invoeren
    public MatchDTO setResults(MatchDTO matchDTO){
        Match match = matchRepository.findById(matchDTO.getId()).orElseThrow();
        match.setScoreBlueTeam(matchDTO.getScoreBlueTeam());
        match.setScoreRedTeam(matchDTO.getScoreRedTeam());
        return matchConverter.matchToMatchDTO(matchRepository.save(match));
    }

    //matchstatsbekijken
    public List<MatchDTO> getAllMatchStats(){
        return matchConverter.matchDTOList(matchRepository.findAll());
    }

    public MatchDTO getById(long id){

        return matchConverter.matchToMatchDTO(matchRepository.findById(id).orElseThrow());
    }

    public List<MatchDTO> getByTeamId(long teamId, UserPrincipal userPrincipal){
        boolean isSpelerRole = userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SPELER"));

        if(isSpelerRole){
            Speler speler = spelerRepository.findByUser(userPrincipal.getUser()).orElseThrow();
            //checks if speler is in team
            SpelerTeam spelerTeam = spelerTeamRepository.findBySpelerIdAndTeamId(speler.getId(),teamId).orElseThrow();
        }

        Team team = teamRepository.findById(teamId).orElseThrow();
        return matchConverter.matchToMatchDTOList(matchRepository.findByTeamBlueOrTeamRed(team, team).orElseThrow()) ;
    }

    public List<MatchDTO> getBySpelerId(long spelerId){
        Speler speler = spelerRepository.findById(spelerId).orElseThrow();
        List<SpelerMatch> spelerMatches = spelerMatchRepository.findBySpeler(speler).orElseThrow();
        List<MatchDTO> matches = new ArrayList<>();
        for (SpelerMatch sp: spelerMatches) {
            Match match = matchRepository.findBySpelersContaining(sp).orElseThrow();
            matches.add(matchConverter.matchToMatchDTO(match));
        }
        return matches;
    }

    //match historiek
    public List<MatchDTO> getAllMatchesHistory(){
        return matchConverter.matchToMatchDTOList(matchRepository.findAll());
    }

    public List<SpelerMatchDTO> allMatchesVanSpeler(){

        List<SpelerMatch> spelers = spelerMatchRepository.findAll();

        return matchConverter.spelerMatchToListDto(spelers);
    }
}
