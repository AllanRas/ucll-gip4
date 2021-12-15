package com.example.demo.Converter;

import com.example.demo.dao.SpelerTeamRepository;
import com.example.demo.domain.Match;
import com.example.demo.domain.Speler;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.MatchDTO;
import com.example.demo.dto.match.MatchStatsDTO;
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
        return match;
    }

    // match to matchDTO
    public CreateMatchDTO matchToCreateMatchDTO(Match match){

        CreateMatchDTO matchDTO = new CreateMatchDTO();
        matchDTO.setTeamBlue(teamConverter.teamToDTO(match.getTeamBlue()));
        matchDTO.setTeamRed(teamConverter.teamToDTO(match.getTeamRed()));
        matchDTO.setDatumtijd(match.getDatumtijd());
        return matchDTO;
    }

    public List<CreateMatchDTO> matchListToDTO(List<Match> matches){
        return matches.stream().map(this::matchToCreateMatchDTO).collect(Collectors.toList());
    }

    // match to matchstatsDTO
    public MatchStatsDTO matchToMatchStatsDTO(Match match){
        MatchStatsDTO matchStatsDTO = new MatchStatsDTO();

        matchStatsDTO.setScoreBlueTeam(match.getScoreBlueTeam());
        matchStatsDTO.setScoreRedTeam(match.getScoreRedTeam());
        return matchStatsDTO;
    }

    public List<MatchStatsDTO> matchStatsDTOList(List<Match> matches){
        return matches.stream().map(this::matchToMatchStatsDTO).collect(Collectors.toList());
    }

    public List<MatchDTO> matchToMatchDTOList(List<Match> matches){
        return matches.stream().map(this::matchToMatchDTO).collect(Collectors.toList());
    }
}
