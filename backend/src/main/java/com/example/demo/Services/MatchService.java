package com.example.demo.Services;

import com.example.demo.Converter.MatchConverter;
import com.example.demo.Converter.TeamConverter;
import com.example.demo.dao.MatchRepository;
import com.example.demo.domain.Match;
import com.example.demo.domain.Speler;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.MatchDTO;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public MatchDTO addMatch(CreateMatchDTO matchDTO){
        Speler speler = new Speler();
        Match match = new Match();

        match.setTeamBlue(teamConverter.DTOtoTeam(matchDTO.getTeamBlue()));
        match.setTeamRed(teamConverter.DTOtoTeam(matchDTO.getTeamRed()));
        match.setSpelers(speler.getMatches());
        matchRepository.save(match);
        return matchConverter.matchToMatchDTO(match);
    }

    //get all matches
    public List<MatchDTO> getAllMatches(){
        return matchConverter.matchListToDTO(matchRepository.findAll());
    }
}
