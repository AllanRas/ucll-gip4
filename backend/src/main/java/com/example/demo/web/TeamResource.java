package com.example.demo.web;


import com.example.demo.Services.ManagerService;
import com.example.demo.Services.TeamService;
import com.example.demo.config.UserPrincipal;
import com.example.demo.dto.ManagerDTO;
import com.example.demo.dto.TeamDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@CrossOrigin("http://localhost:3000/")
public class TeamResource {

    private final TeamService teamService;
    private final ManagerService managerService;

    public TeamResource(TeamService teamService, ManagerService managerService) {
        this.teamService = teamService;
        this.managerService = managerService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TeamDTO createTeam(@RequestBody TeamDTO teamDTO){

        // Get manager from authentication
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ManagerDTO managerDTO = managerService.getManagerByUserId(userPrincipal.getUser());

        return teamService.createTeam(teamDTO, managerDTO.getId());
    }
}
