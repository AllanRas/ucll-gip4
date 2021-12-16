package com.example.demo.Services;

import com.example.demo.Converter.MatchConverter;
import com.example.demo.Converter.TeamConverter;
import com.example.demo.dao.MatchRepository;
import com.example.demo.dao.SpelerMatchRepository;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.dao.TeamRepository;
import com.example.demo.domain.Match;
import com.example.demo.domain.Speler;
import com.example.demo.domain.SpelerMatch;
import com.example.demo.domain.Team;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.MatchDTO;
import com.example.demo.dto.SpelerMatchDTO;
import com.example.demo.dto.match.MatchStatsDTO;
import liquibase.pro.packaged.T;
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


    public MatchService(MatchRepository matchRepository, SpelerMatchRepository spelerMatchRepository, TeamRepository teamRepository, SpelerRepository spelerRepository, MatchConverter matchConverter, TeamConverter teamConverter) {
        this.matchRepository = matchRepository;
        this.spelerMatchRepository = spelerMatchRepository;
        this.teamRepository = teamRepository;
        this.spelerRepository = spelerRepository;
        this.matchConverter = matchConverter;
        this.teamConverter = teamConverter;
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
        }
        return matchConverter.matchToCreateMatchDTO(newmatch);
    }

    public MatchDTO createMatch(MatchDTO matchDTO){
        Match match = new Match();
        Set<SpelerMatchDTO> spelers = matchDTO.getSpelers();

        match.setId(matchDTO.getId());
        match.setDatumtijd(matchDTO.getDatumtijd());
        match.setScoreBlueTeam(matchDTO.getScoreBlueTeam());
        match.setScoreRedTeam(matchDTO.getScoreRedTeam());
        match.setTeamBlue(teamRepository.getById(matchDTO.getTeamBlue().getId()));
        match.setTeamRed(teamRepository.getById(matchDTO.getTeamRed().getId()));
        match.setDatumtijd(matchDTO.getDatumtijd());

        Match newmatch = matchRepository.save(match);

        for (SpelerMatchDTO spelerMatchDTO: spelers) {


            Match matchspeler = matchRepository.getById(newmatch.getId());
            Speler speler = spelerRepository.getById(spelerMatchDTO.getSpelerid());

            SpelerMatch spelerMatch = new SpelerMatch.Builder()
                    .match(matchspeler)
                    .speler(speler)
                    .build();

            spelerMatchRepository.save(spelerMatch);
        }
        return matchConverter.matchToMatchDTO(newmatch);
    }

    //get all matches
    public List<MatchDTO> getAllMatches(){
        return matchConverter.matchListToMatchDTO(matchRepository.findAll());
    }

    //match results invoeren
    public MatchDTO setResults(long id,MatchDTO matchDTO){

        Optional<Match> match = matchRepository.findById(id);

        if (match.isPresent()){
            Match updatedMatch = matchConverter.matchDTOToMatch(matchDTO);
            Match newmatch = match.get();

            newmatch.setScoreBlueTeam(updatedMatch.getScoreBlueTeam());
            newmatch.setScoreRedTeam(updatedMatch.getScoreRedTeam());

            matchRepository.save(newmatch);
        }
        return matchConverter.matchToMatchDTO(match.orElseThrow());
    }

    //matchstatsbekijken
    public List<MatchDTO> getAllMatchStats(){
        return matchConverter.matchDTOList(matchRepository.findAll());
    }

    public MatchDTO getById(long id){
        Optional<Match> match = matchRepository.findById(id);
        matchConverter.matchListToMatchDTO(matchRepository.findAll());

        if (match.isPresent()){
            Match newMatch = match.get();

            matchRepository.save(newMatch);
        }

        return matchConverter.matchToMatchDTO(match.orElseThrow());
    }

    //match historiek
    public List<MatchDTO> getAllMatchesHistory(){
        return matchConverter.matchToMatchDTOList(matchRepository.findAll());
    }
}
