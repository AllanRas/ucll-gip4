package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import com.example.demo.domain.Speler;
import com.example.demo.dto.AdresDTO;
import com.example.demo.dto.SpelerDTO;
import com.example.demo.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



import java.text.SimpleDateFormat;

@SpringBootTest
@AutoConfigureMockMvc
public class SpelerResourceTest extends AbstractIntegrationTest{

    @Autowired
    private WebApplicationContext wac;

    private BCryptPasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        passwordEncoder = new BCryptPasswordEncoder();

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity())
                .build();

    }

    @Test
    void maaktNieuweSpeler() throws Exception {
        // Given
        SpelerDTO josPatat = new SpelerDTO.Builder()
                .actief(true)
                .geboortedatum(new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-11"))
                .user(new UserDTO.Builder()
                        .voornaam("Jos")
                        .achternaam("Patat")
                        .username("JPatat")
                        .password("password")
                        .role("SPELER")
                        .email("Jpatat@gmail.com").build())
                .adres(new AdresDTO.Builder()
                        .gemeente("Leuven")
                        .straat("straat in leuven")
                        .huisnummer("42")
                        .postcode("3000")
                        .build())
                .build();

        // When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/spelers")
                .with(httpBasic("manager","manager"))
                .content(toJson(josPatat))
                .contentType(MediaType.APPLICATION_JSON));
        ResultActions resultActions = perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
        MvcResult result = resultActions.andReturn();
        MockHttpServletResponse response = result.getResponse();
        String json = response.getContentAsString();
        SpelerDTO gemaakteSpeler = fromJson(json, SpelerDTO.class);

        // Then
        // check user equals
        assertEquals(gemaakteSpeler.getUserDTO().getVoornaam(), josPatat.getUserDTO().getVoornaam());
        assertEquals(gemaakteSpeler.getUserDTO().getAchternaam(), josPatat.getUserDTO().getAchternaam());

        // check adres equals
        assertEquals(gemaakteSpeler.getAdresDTO().getGemeente(), josPatat.getAdresDTO().getGemeente());
        assertEquals(gemaakteSpeler.getAdresDTO().getHuisnummer(), josPatat.getAdresDTO().getHuisnummer());
        assertEquals(gemaakteSpeler.getAdresDTO().getStraat(), josPatat.getAdresDTO().getStraat());
    }
}
