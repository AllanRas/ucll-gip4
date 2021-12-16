package com.example.demo.Converter;

import com.example.demo.dao.MatchRepository;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.domain.Match;
import com.example.demo.domain.Speler;
import com.example.demo.domain.SpelerMatch;
import com.example.demo.domain.Team;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.MatchDTO;
import com.example.demo.dto.SpelerMatchDTO;
import com.example.demo.dto.match.MatchStatsDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MatchConverter {

    TeamConverter teamConverter = new TeamConverter();

    private SpelerRepository spelerRepository;
    private MatchRepository matchRepository;

    public MatchConverter(SpelerRepository spelerRepository, MatchRepository matchRepository) {
        this.spelerRepository = spelerRepository;
        this.matchRepository = matchRepository;
    }

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

    public List<MatchDTO> matchDTOList(List<Match> matches){
        return matches.stream().map(this::matchToMatchDTO).collect(Collectors.toList());
    }

    public List<MatchDTO> matchToMatchDTOList(List<Match> matches){
        return matches.stream().map(this::matchToMatchDTO).collect(Collectors.toList());
    }


    // SPELERMATCH CONVERTERS

    public SpelerMatchDTO spelerMatchToDTO(SpelerMatch spelerMatch){
        return new SpelerMatchDTO.Builder()
                .id(spelerMatch.getId())
                .speler(spelerMatch.getSpeler().getId())
                .match(spelerMatch.getMatch().getId())
                .build();
    }

    public SpelerMatch dtoToSpelerMatch(SpelerMatchDTO spelerMatchDTO){
        Speler speler = spelerRepository.findById(spelerMatchDTO.getSpelerid()).orElseThrow();
        Match match = matchRepository.findById(spelerMatchDTO.getMatchid()).get();
        return new SpelerMatch.Builder()
                .id(spelerMatchDTO.getId())
                .speler(speler)
                .match(match)
                .build();
    }

    public Set<SpelerMatch> dtoToSpelerTeamSet(Set<SpelerMatchDTO> spelerMatches){
        return spelerMatches.stream().map(this::dtoToSpelerMatch).collect(Collectors.toSet());
    }

    public Set<SpelerMatchDTO> spelerTeamSetToDTO(Set<SpelerMatch> spelerMatches){
        return spelerMatches.stream().map(this::spelerMatchToDTO).collect(Collectors.toSet());
    }

}
