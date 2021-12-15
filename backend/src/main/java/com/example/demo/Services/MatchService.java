package com.example.demo.Services;

import com.example.demo.Converter.MatchConverter;
import com.example.demo.Converter.TeamConverter;
import com.example.demo.dao.MatchRepository;
import com.example.demo.domain.Match;
import com.example.demo.domain.Speler;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.MatchDTO;
import com.example.demo.dto.SpelerDTO;
import com.example.demo.dto.match.MatchStatsDTO;
import liquibase.pro.packaged.M;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchConverter matchConverter;
    private final TeamConverter teamConverter;


    public MatchService(MatchRepository matchRepository, MatchConverter matchConverter, TeamConverter teamConverter) {
        this.matchRepository = matchRepository;
        this.matchConverter = matchConverter;
        this.teamConverter = teamConverter;
    }

    //Create Match
    public CreateMatchDTO addMatch(CreateMatchDTO matchDTO){
        Match match = new Match();

        match.setTeamBlue(teamConverter.DTOtoTeam(matchDTO.getTeamBlue()));
        match.setTeamRed(teamConverter.DTOtoTeam(matchDTO.getTeamRed()));
        match.setScoreBlueTeam(matchDTO.getScoreBlueTeam());
        match.setScoreRedTeam(matchDTO.getScoreRedTeam());
        matchRepository.save(match);
        return matchConverter.matchToCreateMatchDTO(match);
    }

    //get all matches
    public List<CreateMatchDTO> getAllMatches(){
        return matchConverter.matchListToDTO(matchRepository.findAll());
    }

    //match results invoeren
    public MatchStatsDTO setResults(MatchStatsDTO matchStatsDTO){
        Match match = new Match();

        match.setScoreBlueTeam(matchStatsDTO.getScoreBlueTeam());
        match.setScoreRedTeam(matchStatsDTO.getScoreRedTeam());
        matchRepository.save(match);
        return matchConverter.matchToMatchStatsDTO(match);
    }

    //matchstatsbekijken
    public List<MatchStatsDTO> getAllMatchStats(){
        return matchConverter.matchStatsDTOList(matchRepository.findAll());
    }

    public MatchDTO getById(long id){
        Optional<Match> match = matchRepository.findById(id);
        return matchConverter.matchToMatchDTO(match.orElseThrow());
    }

    //match historiek
    public List<MatchDTO> getAllMatchesHistory(){
        return matchConverter.matchToMatchDTOList(matchRepository.findAll());
    }
}
