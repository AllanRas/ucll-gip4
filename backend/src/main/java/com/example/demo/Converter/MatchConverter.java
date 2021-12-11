package com.example.demo.Converter;

import com.example.demo.domain.Match;
import com.example.demo.domain.Speler;
import com.example.demo.dto.MatchDTO;
import com.example.demo.dto.SpelerDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatchConverter {

    TeamConverter teamConverter = new TeamConverter();

    // match to matchDTO
    public MatchDTO matchToMatchDTO(Match match){

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(match.getId());
        matchDTO.setTeamBlue(teamConverter.teamToDTO(match.getTeamBlue()));
        matchDTO.setTeamRed(teamConverter.teamToDTO(match.getTeamRed()));
        matchDTO.setScoreBlueTeam(match.getScoreBlueTeam());
        matchDTO.setScoreRedTeam(match.getScoreRedTeam());
        matchDTO.setDatumtijd(match.getDatumtijd());
        matchDTO.setSpeler(match.getSpelers());
        return matchDTO;
    }

    // matchDTO to match
    public Match matchDTOToMatch(MatchDTO matchDTO){

        Match match = new Match();
        match.setId(matchDTO.getId());
        match.setTeamBlue(teamConverter.DTOtoTeam(matchDTO.getTeamBlue()));
        match.setTeamRed(teamConverter.DTOtoTeam(matchDTO.getTeamRed()));
        match.setScoreBlueTeam(matchDTO.getScoreBlueTeam());
        match.setScoreRedTeam(matchDTO.getScoreRedTeam());
        match.setDatumtijd(matchDTO.getDatumtijd());
        match.setSpelers(matchDTO.getSpeler());
        return match;
    }

    public List<MatchDTO> matchListToDTO(List<Match> matches){
        return matches.stream().map(this::matchToMatchDTO).collect(Collectors.toList());
    }
}
