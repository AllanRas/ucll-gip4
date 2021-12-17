package com.example.demo.Services;

import com.example.demo.Converter.MatchConverter;
import com.example.demo.Converter.TeamConverter;
import com.example.demo.Services.emailService.EmailSenderService;
import com.example.demo.dao.MatchRepository;
import com.example.demo.dao.SpelerMatchRepository;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.dao.TeamRepository;
import com.example.demo.domain.Match;
import com.example.demo.domain.Speler;
import com.example.demo.domain.SpelerMatch;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.MatchDTO;
import com.example.demo.dto.SpelerMatchDTO;
import com.example.demo.dto.match.MatchStatsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final SpelerMatchRepository spelerMatchRepository;
    private final TeamRepository teamRepository;
    private final SpelerRepository spelerRepository;
    private final MatchConverter matchConverter;
    private final TeamConverter teamConverter;
    private final EmailSenderService emailSenderService;


    public MatchService(MatchRepository matchRepository, SpelerMatchRepository spelerMatchRepository, TeamRepository teamRepository, SpelerRepository spelerRepository, MatchConverter matchConverter, TeamConverter teamConverter, EmailSenderService emailSenderService) {
        this.matchRepository = matchRepository;
        this.spelerMatchRepository = spelerMatchRepository;
        this.teamRepository = teamRepository;
        this.spelerRepository = spelerRepository;
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
    public List<MatchStatsDTO> getAllMatchStats(){
        return matchConverter.matchStatsDTOList(matchRepository.findAll());
    }

    public MatchDTO getById(long id){
        return matchConverter.matchToMatchDTO(matchRepository.findById(id).orElseThrow());
    }

    //match historiek
    public List<MatchDTO> getAllMatchesHistory(){
        return matchConverter.matchToMatchDTOList(matchRepository.findAll());
    }
}
