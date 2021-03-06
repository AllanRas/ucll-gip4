package com.example.demo.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;


import com.example.demo.AbstractIntegrationTest;
import com.example.demo.Services.ManagerService;
import com.example.demo.Services.SpelerService;
import com.example.demo.domain.Team;
import com.example.demo.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


import javax.transaction.Transactional;
import java.text.SimpleDateFormat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SpelerResourceTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private SpelerService spelerService;

    @Autowired
    private ManagerService managerService;

    private MockMvc mockMvc;

    private CreateSpelerDTO josPatat;

    private CreateSpelerDTO bert;

    private ManagerDTO jefManager;

    @BeforeEach
    void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity())
                .build();

        josPatat = new CreateSpelerDTO.Builder()
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

        bert = new CreateSpelerDTO.Builder()
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
                .build();

        jefManager = new ManagerDTO.Builder()
                .passwoord("password")
                .userDTO(new UserDTO.Builder()
                        .voornaam("Jef")
                        .achternaam("De Manager")
                        .username("JefkeM")
                        .email("JefkeM@gmail.com").build())
                .build();

    }

    @Test
    void maaktNieuweSpeler() throws Exception {
        // Given

        // When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/spelers")
                .with(httpBasic("manager","manager"))
                .content(toJson(josPatat))
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult result = perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        SpelerDTO gemaakteSpeler = fromMvcResult(result,SpelerDTO.class);

        // Then
        // check user equals
        assertEquals(gemaakteSpeler.getUserDTO().getVoornaam(), josPatat.getUserDTO().getVoornaam());
        assertEquals(gemaakteSpeler.getUserDTO().getAchternaam(), josPatat.getUserDTO().getAchternaam());

        // check adres equals
        assertEquals(gemaakteSpeler.getAdresDTO().getGemeente(), josPatat.getAdresDTO().getGemeente());
        assertEquals(gemaakteSpeler.getAdresDTO().getHuisnummer(), josPatat.getAdresDTO().getHuisnummer());
        assertEquals(gemaakteSpeler.getAdresDTO().getStraat(), josPatat.getAdresDTO().getStraat());
    }

    @Test
    void updateSpelerAsSpeler() throws Exception {
        // Given
        SpelerDTO josPatatDTO = spelerService.createSpeler(josPatat);
        SpelerDTO bertDTO = spelerService.createSpeler(bert);

        // update de voornaam en achternaam
        SpelerDTO josUpdated = new SpelerDTO.Builder()
                .id((long)0)
                .actief(true)
                .geboortedatum(new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-11"))
                .user(new UserDTO.Builder()
                        .voornaam("Josupdated")
                        .achternaam("Patatupdated")
                        .username("JPatat")
                        .email("Jpatat@gmail.com")
                        .role("SPELER")
                        .id((long)0)
                        .build())
                .adres(new AdresDTO.Builder()
                        .gemeente("Leuven")
                        .straat("straat in leuven")
                        .huisnummer("42")
                        .postcode("3000")
                        .id((long)0)
                        .build())
                .build();

        //When
        //update speler eigen gegevens
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.put("/spelers/{id}/update", josPatatDTO.getId())
                .with(httpBasic(josPatat.getUserDTO().getUsername(),josPatat.getPassword()))
                .content(toJson(josUpdated))
                .contentType(MediaType.APPLICATION_JSON)
        );

        MvcResult result =  perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        // update speler met een andere speler geeft Forbidden 403
        ResultActions perform2 = this.mockMvc.perform(MockMvcRequestBuilders.put("/spelers/{id}/update", josPatatDTO.getId())
                        .with(httpBasic(bert.getUserDTO().getUsername(),bert.getPassword()))
                        .content(toJson(bertDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        perform2.andExpect(status().isForbidden());

        SpelerDTO  updatedSpeler= fromMvcResult(result,SpelerDTO.class);

        // Then
        assertNotEquals(updatedSpeler.getUserDTO().getVoornaam(), josPatat.getUserDTO().getVoornaam());
        assertNotEquals(updatedSpeler.getUserDTO().getAchternaam(), josPatat.getUserDTO().getAchternaam());
    }

    @Test
    void updateSpelerAsManager() throws Exception {
        // Given
        SpelerDTO josPatatDTO = spelerService.createSpeler(josPatat);

        // update de voornaam en achternaam
        SpelerDTO josUpdated = new SpelerDTO.Builder()
                .id((long)0)
                .actief(true)
                .geboortedatum(new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-11"))
                .user(new UserDTO.Builder()
                        .voornaam("Josupdated")
                        .achternaam("Patatupdated")
                        .username("JPatat")
                        .email("Jpatat@gmail.com")
                        .role("SPELER")
                        .id((long)0)
                        .build())
                .adres(new AdresDTO.Builder()
                        .gemeente("Leuven")
                        .straat("straat in leuven")
                        .huisnummer("42")
                        .postcode("3000")
                        .id((long)0)
                        .build())
                .build();

        //When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.put("/spelers/{id}/update", josPatatDTO.getId())
                .with(httpBasic(josPatat.getUserDTO().getUsername(),josPatat.getPassword()))
                .content(toJson(josUpdated))
                .contentType(MediaType.APPLICATION_JSON)
        );

        MvcResult result =  perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        SpelerDTO  updatedSpeler= fromMvcResult(result,SpelerDTO.class);

        // Then
        assertNotEquals(updatedSpeler.getUserDTO().getVoornaam(), josPatat.getUserDTO().getVoornaam());
        assertNotEquals(updatedSpeler.getUserDTO().getAchternaam(), josPatat.getUserDTO().getAchternaam());
    }

    @Test
    void getOneSpelerAsManager() throws Exception{
        // Given
        SpelerDTO josPatatDTO = spelerService.createSpeler(josPatat);
        ManagerDTO jefManagerDTO = managerService.createManager(jefManager);

        //When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/spelers/{id}/getOne",josPatatDTO.getId())
                .with(httpBasic(jefManager.getUserDTO().getUsername(), jefManager.getPasswoord())));

        MvcResult result = perform
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        SpelerDTO speler = fromMvcResult(result, SpelerDTO.class);

        // then
        assertEquals(speler.getUserDTO().getVoornaam(), josPatat.getUserDTO().getVoornaam());
        assertEquals(speler.getUserDTO().getAchternaam(), josPatat.getUserDTO().getAchternaam());
    }

    @Test
    void getOneSpelerAsSpeler() throws Exception{
        // Given
        SpelerDTO josPatatDTO = spelerService.createSpeler(josPatat);

        //When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/spelers/{id}/getOne",josPatatDTO.getId())
                .with(httpBasic(josPatat.getUserDTO().getUsername(), josPatat.getPassword())));

        MvcResult result = perform
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        SpelerDTO speler = fromMvcResult(result, SpelerDTO.class);

        // then
        assertEquals(speler.getUserDTO().getVoornaam(), josPatat.getUserDTO().getVoornaam());
        assertEquals(speler.getUserDTO().getAchternaam(), josPatat.getUserDTO().getAchternaam());
    }

    @Test
    void getAllSpelers() throws Exception{
        // manager auth return 400 : Ok
        this.mockMvc.perform(MockMvcRequestBuilders.get("/spelers")
                        .with(httpBasic("manager","manager"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // speler auth returns 403 : Forbidden
        this.mockMvc.perform(MockMvcRequestBuilders.get("/spelers")
                        .with(httpBasic("speler","speler"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        // foute auth returns 401 : Unauthorized
        this.mockMvc.perform(MockMvcRequestBuilders.get("/spelers")
                .with(httpBasic("",""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void setSpelerInactive() throws Exception{
        // Given
        CreateSpelerDTO josPatat = new CreateSpelerDTO.Builder()
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

        SpelerDTO josPatatDTO = spelerService.createSpeler(josPatat);

        // When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.put("/spelers/{id}/delete",josPatatDTO.getId())
                .with(httpBasic("manager","manager"))
                .content(toJson(josPatat))
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult result = perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        SpelerDTO gemaakteSpeler = fromMvcResult(result, SpelerDTO.class);

        // then
        assertNotEquals(gemaakteSpeler.isActief(), josPatat.isActief());
    }
}
