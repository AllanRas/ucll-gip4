package com.example.demo.Converter;

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
    SpelerConverter spelerConverter = new SpelerConverter();

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

    /**
    public Team DTOtoTeam(TeamDTO teamDTO){
        Team team = new Team();
        team.setId(teamDTO.getId());
        team.setNaam(teamDTO.getNaam());
        team.setManager(managerConverter.dtoToManager(teamDTO.getManagerDTO()));
        team.setActief(team.isActief());
        team.setSpelers(teamDTO.getSpelerDTO());
        team.setMatchesteamRed(teamDTO.getMatchesteamRed());
        team.setMatchesteamBlue(teamDTO.getMatchesteamBlue());

        return team;
    }
     **/

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

    public Set<SpelerTeamDTO> spelerTeamSetToDTO(Set<SpelerTeam> spelerTeams){
        return spelerTeams.stream().map(this::spelerTeamToDTO).collect(Collectors.toSet());
    }
}
