package com.example.demo.Services;
import com.example.demo.Converter.ManagerConverter;
import com.example.demo.Converter.TeamConverter;
import com.example.demo.dao.ManagerRepository;
import com.example.demo.dao.TeamRepository;
import com.example.demo.domain.Manager;
import com.example.demo.domain.Team;
import com.example.demo.dto.TeamDTO;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ManagerRepository managerRepository;

    private final TeamConverter teamConverter;
    private final ManagerConverter managerConverter;

    public TeamService(TeamRepository teamRepository, ManagerRepository managerRepository, TeamConverter teamConverter, ManagerConverter managerConverter) {
        this.teamRepository = teamRepository;
        this.managerRepository = managerRepository;
        this.teamConverter = teamConverter;
        this.managerConverter = managerConverter;
    }

    public TeamDTO createTeam(TeamDTO teamDTO, Long managerId){

        Manager manager = managerRepository.findById(managerId).orElseThrow();

        System.out.println(manager.getUser().getVoornaam() + " " + manager.getUser().getAchternaam());

        teamDTO.setManagerDTO(managerConverter.managerDTO(manager));
        teamDTO.setActief(true);

        Team team = teamConverter.DTOtoTeam(teamDTO);

        System.out.println(team.toString());
        teamRepository.save(team);
        return teamConverter.teamToDTO(team);
    }



}
