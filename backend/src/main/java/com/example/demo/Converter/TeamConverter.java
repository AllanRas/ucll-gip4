package com.example.demo.Converter;

import com.example.demo.domain.Team;
import com.example.demo.dto.CreateTeamDTO;
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
        if(team.getSpelers() != null){
            teamDTO.setSpelerDTO(team.getSpelers());
        }
        if(team.getMatches() != null){
            teamDTO.setMatch(team.getMatches());
        }
        return teamDTO;
    }

    public Team DTOtoTeam(TeamDTO teamDTO){
        Team team = new Team();
        team.setId(teamDTO.getId());
        team.setNaam(teamDTO.getNaam());
        team.setManager(managerConverter.dtoToManager(teamDTO.getManagerDTO()));
        team.setActief(team.isActief());
        if(teamDTO.getSpelerDTO() != null){
            team.setSpelers(teamDTO.getSpelerDTO());
        }
        if(teamDTO.getMatch() != null){
            team.setMatches(teamDTO.getMatch());
        }
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

    public List<TeamDTO> teamListToDTO(List<Team> teams){
        return teams.stream().map(this::teamToDTO).collect(Collectors.toList());
    }

}
