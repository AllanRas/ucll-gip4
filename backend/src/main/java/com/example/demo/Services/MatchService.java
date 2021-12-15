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
        return matchConverter.matchToCreateMatchDTO(newmatch);
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
