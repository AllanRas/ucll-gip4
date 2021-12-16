package com.example.demo.Converter;

import com.example.demo.dao.MatchRepository;
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
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MatchConverter {

    TeamConverter teamConverter = new TeamConverter();

    private SpelerRepository spelerRepository;
    private MatchRepository matchRepository;
    private TeamRepository teamRepository;

    public MatchConverter(SpelerRepository spelerRepository, MatchRepository matchRepository, TeamRepository teamRepository) {
        this.spelerRepository = spelerRepository;
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
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

        Team teamBlue = teamRepository.getById(matchDTO.getTeamBlue().getId());
        Team teamRed = teamRepository.getById(matchDTO.getTeamRed().getId());

        Match match = new Match();
        match.setId(matchDTO.getId());
        match.setTeamBlue(teamBlue);
        match.setTeamRed(teamRed);
        match.setScoreBlueTeam(matchDTO.getScoreBlueTeam());
        match.setScoreRedTeam(matchDTO.getScoreRedTeam());
        match.setDatumtijd(matchDTO.getDatumtijd());
        return match;
    }

    // match to matchDTO
    public CreateMatchDTO matchToCreateMatchDTO(Match match){


        CreateMatchDTO matchDTO = new CreateMatchDTO();
        matchDTO.setTeamBlueId(match.getTeamBlue().getId());
        matchDTO.setTeamRedId(match.getTeamRed().getId());
        matchDTO.setDatumtijd(match.getDatumtijd());
        return matchDTO;
    }

    public List<CreateMatchDTO> matchListToDTO(List<Match> matches){
        return matches.stream().map(this::matchToCreateMatchDTO).collect(Collectors.toList());
    }

    public List<MatchDTO> matchListToMatchDTO(List<Match> matches){
        return matches.stream().map(this::matchToMatchDTO).collect(Collectors.toList());
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


    // SPELERMATCH CONVERTERS

    public SpelerMatchDTO spelerMatchToDTO(SpelerMatch spelerMatch){
        return new SpelerMatchDTO.Builder()
                .id(spelerMatch.getId())
                .speler(spelerMatch.getSpeler().getId())
                .match(spelerMatch.getMatch().getId())
                .teamId(spelerMatch.getTeamid())
                .build();
    }

    public SpelerMatch dtoToSpelerMatch(SpelerMatchDTO spelerMatchDTO){
        Speler speler = spelerRepository.findById(spelerMatchDTO.getSpelerid()).orElseThrow();
        Match match = matchRepository.findById(spelerMatchDTO.getMatchid()).get();
        return new SpelerMatch.Builder()
                .id(spelerMatchDTO.getId())
                .speler(speler)
                .match(match)
                .teamid(spelerMatchDTO.getTeamid())
                .build();
    }

    public Set<SpelerMatch> dtoToSpelerTeamSet(Set<SpelerMatchDTO> spelerMatches){
        return spelerMatches.stream().map(this::dtoToSpelerMatch).collect(Collectors.toSet());
    }

    public Set<SpelerMatchDTO> spelerTeamSetToDTO(Set<SpelerMatch> spelerMatches){
        return spelerMatches.stream().map(this::spelerMatchToDTO).collect(Collectors.toSet());
    }
}
