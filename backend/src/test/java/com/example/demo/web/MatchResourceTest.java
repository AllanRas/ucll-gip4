package com.example.demo.web;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.Services.ManagerService;
import com.example.demo.Services.MatchService;
import com.example.demo.Services.SpelerService;
import com.example.demo.Services.TeamService;
import com.example.demo.dao.ManagerRepository;
import com.example.demo.domain.Manager;
import com.example.demo.domain.Speler;
import com.example.demo.dto.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MatchResourceTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private SpelerService spelerService;

    private MockMvc mockMvc;
    private CreateMatchDTO match1;
    private CreateTeamDTO createTeam1;
    private CreateTeamDTO createTeam2;
    private TeamDTO team1;
    private TeamDTO team2;
    private ManagerDTO Jefmanager;
    private SpelerDTO josPatat;
    private SpelerDTO bert;

    @BeforeEach
    void setUp() throws Exception{

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity())
                .build();


        Jefmanager = managerService.createManager(new ManagerDTO.Builder()
                .passwoord("password")
                .userDTO(new UserDTO.Builder()
                        .voornaam("Jef")
                        .achternaam("De Manager")
                        .username("JefkeM")
                        .email("JefkeM@gmail.com").build())
                .build());


        createTeam1 = teamService.createTeam(
                new CreateTeamDTO.Builder()
                        .naam("TestBlue")
                        .managerDTO(Jefmanager)
                        .build(), Jefmanager.getId());

        createTeam2 = teamService.createTeam(
                new CreateTeamDTO.Builder()
                        .naam("TestRed")
                        .actief(true)
                        .managerDTO(Jefmanager)
                        .build(), Jefmanager.getId()
        );

        josPatat = spelerService.createSpeler(
                new CreateSpelerDTO.Builder()
                        .actief(true)
                        .geboortedatum(new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-11"))
                        .password("password")
                        .user(new UserDTO.Builder()
                                .voornaam("Jos")
                                .achternaam("Patat")
                                .username("JPatat")
                                .role("SPELER")
                                .email("Jpatat@gmail.com").build())
                        .adres(new AdresDTO.Builder()
                                .gemeente("Leuven")
                                .straat("straat in leuven")
                                .huisnummer("42")
                                .postcode("3000")
                                .build())
                        .build());

        bert = spelerService.createSpeler(
                new CreateSpelerDTO.Builder()
                .actief(true)
                .geboortedatum(new SimpleDateFormat("yyyy-MM-dd").parse("1992-07-03"))
                .password("password")
                .user(new UserDTO.Builder()
                        .voornaam("bert")
                        .achternaam("achternaam")
                        .username("BertA")
                        .role("SPELER")
                        .email("BertA@gmail.com").build())
                .adres(new AdresDTO.Builder()
                        .gemeente("Leuven")
                        .straat("straat in leuven")
                        .huisnummer("42")
                        .postcode("3000")
                        .build())
                .build());
    }

    @Test
    public void addMatch() throws Exception{
        //Given
        team1 = teamService.getTeamById(createTeam1.getId());
        team2 = teamService.getTeamById(createTeam2.getId());

        SpelerMatchDTO spelerMatchDTO1 = new SpelerMatchDTO.Builder()
                .speler(josPatat.getId())
                .build();

        SpelerMatchDTO spelerMatchDTO2 = new SpelerMatchDTO.Builder()
                .speler(bert.getId())
                .build();

        List<SpelerMatchDTO> spelers = new ArrayList<>();

        spelers.add(spelerMatchDTO1);
        spelers.add(spelerMatchDTO2);

        match1 = new CreateMatchDTO.Builder()
                .datumtijd(new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-11"))
                .teamBlue(team1)
                .teamRed(team2)
                .spelers(new HashSet<>(spelers))
                .build();

        //When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/match")
                        .with(httpBasic("manager","manager"))
                        .content(toJson(match1))
                        .contentType(MediaType.APPLICATION_JSON));
        MvcResult result = perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        CreateMatchDTO gemaakteMatch = fromMvcResult(result,CreateMatchDTO.class);

        //Then
        assertEquals(gemaakteMatch.getId(), match1.getId());
        assertEquals(gemaakteMatch.getTeamRed().getNaam(), match1.getTeamRed().getNaam());
        assertEquals(gemaakteMatch.getTeamBlue().getNaam(),match1.getTeamBlue().getNaam());
    }
}