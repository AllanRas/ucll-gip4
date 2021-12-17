package com.example.demo.Converter;

import com.example.demo.dao.SpelerTeamRepository;
import com.example.demo.domain.SpelerTeam;
import com.example.demo.domain.Team;
import com.example.demo.dto.CreateTeamDTO;
import com.example.demo.dto.SpelerTeamDTO;
import com.example.demo.domain.Match;
import com.example.demo.domain.Team;
import com.example.demo.dto.CreateMatchDTO;

import com.example.demo.dto.TeamDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TeamConverter {

    ManagerConverter managerConverter = new ManagerConverter();

    private SpelerTeamRepository spelerTeamRepository;

    public TeamConverter() {
    }

    public TeamConverter(SpelerTeamRepository spelerTeamRepository) {
        this.spelerTeamRepository = spelerTeamRepository;
    }

    // user to userDTO
    public TeamDTO teamToDTO(Team team){

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setNaam(team.getNaam());
        teamDTO.setId(team.getId());
        teamDTO.setManagerDTO(managerConverter.managerDTO(team.getManager()));
        teamDTO.setActief(team.isActief());
        teamDTO.setSpelerDTO(spelerTeamSetToDTO(team.getSpelers()));
        teamDTO.setMatchesteamRed(team.getMatchesteamRed());
        teamDTO.setMatchesteamBlue(team.getMatchesteamBlue());
        return teamDTO;
    }


    public Team DTOtoTeam(TeamDTO teamDTO){
        Team team = new Team();
        team.setId(teamDTO.getId());
        team.setNaam(teamDTO.getNaam());
        team.setManager(managerConverter.dtoToManager(teamDTO.getManagerDTO()));
        team.setActief(team.isActief());
        team.setSpelers(dtoToSpelerTeamSet(teamDTO.getSpelerDTO()));
        team.setMatchesteamRed(teamDTO.getMatchesteamRed());
        team.setMatchesteamBlue(teamDTO.getMatchesteamBlue());

        return team;
    }


    public Team createDTOtoTeam(CreateTeamDTO createTeamDTO){
        Team team = new Team();
        team.setId(createTeamDTO.getId());
        team.setNaam(createTeamDTO.getNaam());
        team.setActief(createTeamDTO.isActief());
        team.setManager(managerConverter.dtoToManager(createTeamDTO.getManagerDTO()));
        return team;
    }

    public CreateTeamDTO createTeamToDTO(Team team){
        CreateTeamDTO createTeamDTO = new CreateTeamDTO();
        createTeamDTO.setNaam(team.getNaam());
        createTeamDTO.setId(team.getId());
        createTeamDTO.setActief(team.isActief());
        createTeamDTO.setManagerDTO(managerConverter.managerDTO(team.getManager()));
        return createTeamDTO;
    }

    //Test
    public TeamDTO createDTOtoTeamDTO(CreateTeamDTO createTeamDTO){
        TeamDTO team = new TeamDTO();
        team.setId(createTeamDTO.getId());
        team.setNaam(createTeamDTO.getNaam());
        team.setActief(createTeamDTO.isActief());
        return team;
    }


    public List<TeamDTO> teamListToDTO(List<Team> teams){
        return teams.stream().map(this::teamToDTO).collect(Collectors.toList());
    }


    // spelerTeam to DTO
    public SpelerTeamDTO spelerTeamToDTO(SpelerTeam spelerTeam){
        return new SpelerTeamDTO.Builder()
                .id(spelerTeam.getId())
                .speler(spelerTeam.getSpeler().getId())
                .reserve(spelerTeam.isReserve())
                .team(spelerTeam.getTeam().getId())
                .build();
    }

    public SpelerTeam dtoToSpelerTeam(SpelerTeamDTO spelerTeamDTO){
        SpelerTeam spelerTeam = spelerTeamRepository.findBySpelerIdAndTeamId(spelerTeamDTO.getSpelerid(), spelerTeamDTO.getTeamid()).orElseThrow();
        return new SpelerTeam.Builder()
                .id(spelerTeamDTO.getId())
                .speler(spelerTeam.getSpeler())
                .reserve(spelerTeamDTO.isReserve())
                .team(spelerTeam.getTeam())
                .build();
    }

    public Set<SpelerTeam> dtoToSpelerTeamSet(Set<SpelerTeamDTO> spelerTeams){
        return spelerTeams.stream().map(this::dtoToSpelerTeam).collect(Collectors.toSet());
    }

    public Set<SpelerTeamDTO> spelerTeamSetToDTO(Set<SpelerTeam> spelerTeams){
        return spelerTeams.stream().map(this::spelerTeamToDTO).collect(Collectors.toSet());
    }
}
