package com.example.demo.web;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.Converter.MatchConverter;
import com.example.demo.Converter.SpelerConverter;
import com.example.demo.Converter.TeamConverter;
import com.example.demo.Services.ManagerService;
import com.example.demo.Services.MatchService;
import com.example.demo.Services.SpelerService;
import com.example.demo.Services.TeamService;
import com.example.demo.dao.ManagerRepository;
import com.example.demo.domain.Manager;
import com.example.demo.domain.Match;
import com.example.demo.domain.Speler;
import com.example.demo.dto.*;
import com.example.demo.dto.match.MatchStatsDTO;
import com.example.demo.dto.match.MatchStatsDTO;
import com.example.demo.dto.match.MatchStatsDTO;
import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.text.ParseException;
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
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private SpelerService spelerService;

    @Autowired
    private TeamConverter converter;

    @Autowired
    private SpelerConverter spelerConverter;

    private MockMvc mockMvc;
    private CreateMatchDTO match1;
    private CreateMatchDTO match2;
    private CreateTeamDTO createTeam1;
    private CreateTeamDTO createTeam2;
    private TeamDTO team1;
    private TeamDTO team2;
    private ManagerDTO Jefmanager;
    private SpelerDTO josPatat;
    private SpelerDTO bert;
    private  CreateSpelerDTO joske;

    @BeforeEach
    void setUp() throws ParseException {

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
                        .managerDTO(Jefmanager)
                        .build(), Jefmanager.getId()
        );


        CreateSpelerDTO joske = new CreateSpelerDTO.Builder()
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
                .build();

        josPatat = spelerService.createSpeler(joske);


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
                .teamId(team1.getId())
                .build();

        SpelerMatchDTO spelerMatchDTO2 = new SpelerMatchDTO.Builder()
                .speler(bert.getId())
                .teamId(team2.getId())
                .build();

        Set<SpelerMatchDTO> spelers = new HashSet<>();

        spelers.add(spelerMatchDTO1);
        spelers.add(spelerMatchDTO2);

        match1 = new CreateMatchDTO.Builder()
                .datumtijd(new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-11"))
                .teamBlue(team1.getId())
                .teamRed(team2.getId())
                .spelers(new HashSet<>(spelers))
                .build();


        //When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/matches")
                        .with(httpBasic("manager","manager"))
                        .content(toJson(match1))
                        .contentType(MediaType.APPLICATION_JSON));

        MvcResult result = perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        CreateMatchDTO gemaakteMatch = fromMvcResult(result,CreateMatchDTO.class);

        //Then
        assertEquals(gemaakteMatch.getTeamRedId(), match1.getTeamRedId());
        assertEquals(gemaakteMatch.getTeamBlueId(), match1.getTeamBlueId());
    }

    @Test
    void getAllMatches() throws Exception{
        // manager auth return 400 : Ok
        this.mockMvc.perform(MockMvcRequestBuilders.get("/matches")
                        .with(httpBasic("manager","manager"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // speler auth returns 403 : Forbidden
        this.mockMvc.perform(MockMvcRequestBuilders.get("/matches")
                        .with(httpBasic("speler","speler"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        // foute auth returns 401 : Unauthorized
        this.mockMvc.perform(MockMvcRequestBuilders.get("/matches")
                        .with(httpBasic("",""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void matchResultsInvoeren() throws Exception{
        // Given
        team1 = teamService.getTeamById(createTeam1.getId());
        team2 = teamService.getTeamById(createTeam2.getId());


        SpelerMatchDTO spelerMatchDTO1 =  new SpelerMatchDTO.Builder()
                .speler(josPatat.getId())
                .teamId(team1.getId())
                .build();

        SpelerMatchDTO spelerMatchDTO2 = new SpelerMatchDTO.Builder()
                .speler(bert.getId())
                .teamId(team2.getId())
                .build();

        Set<SpelerMatchDTO> spelers = new HashSet<>();

        spelers.add(spelerMatchDTO1);
        spelers.add(spelerMatchDTO2);

        CreateMatchDTO created = matchService.addMatch(new CreateMatchDTO.Builder()
                .datumtijd(new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-11"))
                .teamBlue(team1.getId())
                .teamRed(team2.getId())
                .spelers(new HashSet<>(spelers))
                .build());

        MatchDTO matchDTOUpdated = matchService.getById(created.getId());

        matchDTOUpdated.setScoreBlueTeam(5);
        matchDTOUpdated.setScoreRedTeam(7);

        // When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.put("/matches/matchresult")
                .with(httpBasic("manager", "manager"))
                .content(toJson(matchDTOUpdated))
                .contentType(MediaType.APPLICATION_JSON)
        );

        MvcResult result =  perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        MatchDTO updated = fromMvcResult(result, MatchDTO.class);

        // Then
        assertNotEquals(updated.getScoreBlueTeam(), 0);
        assertNotEquals(updated.getScoreRedTeam(), 0);
        assertEquals(updated.getScoreRedTeam(), 7);
        assertEquals(updated.getScoreBlueTeam(), 5);

    }

    @Test
    void matchStatsVanAlleTeams() throws Exception {
        //Manager
        this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchstats/allteam")
                    .with(httpBasic("manager","manager"))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        //Foute Auth
        this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchstats/allteam")
                        .with(httpBasic("",""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void matchStatsVan1Team() throws Exception {
        // Given
        team1 = teamService.getTeamById(createTeam1.getId());

        teamService.addSpelerToTeam(josPatat.getId(), team1.getId(), false, Jefmanager.getId());

        //Manager
        this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchstats/{id}", team1.getId())
                        .with(httpBasic(Jefmanager.getUserDTO().getUsername(),"password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Speler
        this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchstats/{id}", team1.getId())
                        .with(httpBasic(josPatat.getUserDTO().getUsername(),"password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // foute auth
        this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchstats/{id}", team1.getId())
                        .with(httpBasic("",""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }



    //SPELER

    @Test
    void eigenPreviousMatches() throws Exception{

        Speler speler = spelerConverter.dtoToSpeler(spelerService.getById(josPatat.getId()));

        // Speler
        this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchhistory/speler")
                        .with(httpBasic(speler.getUser().getUsername(),"password")))
                .andExpect(status().isOk());
    }

}