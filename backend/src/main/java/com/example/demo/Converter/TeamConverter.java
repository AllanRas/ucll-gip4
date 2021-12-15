package com.example.demo.Converter;

import com.example.demo.domain.Match;
import com.example.demo.domain.Team;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.TeamDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamConverter {

    ManagerConverter managerConverter = new ManagerConverter();

    // user to userDTO
    public TeamDTO teamToDTO(Team team){

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setNaam(team.getNaam());
        teamDTO.setId(team.getId());
        teamDTO.setManagerDTO(managerConverter.managerDTO(team.getManager()));
        teamDTO.setActief(team.isActief());
        teamDTO.setSpelerDTO(team.getSpelers());
        teamDTO.setMatch(team.getMatches());
        teamDTO.setReserveSpelerTeam(team.getReservespelers());
        return teamDTO;
    }

    public Team DTOtoTeam(TeamDTO teamDTO){
        Team team = new Team();
        team.setId(teamDTO.getId());
        team.setNaam(teamDTO.getNaam());
        team.setManager(managerConverter.dtoToManager(teamDTO.getManagerDTO()));
        team.setActief(team.isActief());
        team.setSpelers(teamDTO.getSpelerDTO());
        team.setMatches(teamDTO.getMatch());
        team.setReservespelers(teamDTO.getReserveSpelerTeam());
        return team;
    }

    public List<TeamDTO> teamListToDTO(List<Team> teams){
        return teams.stream().map(this::teamToDTO).collect(Collectors.toList());
    }
}
