package com.example.demo.web;


import com.example.demo.dao.SpelerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.example.demo.domain.Speler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spelers")
public class SpelerResource {

    private final SpelerRepository spelerRepository;

    public SpelerResource(SpelerRepository spelerRepository){
        this.spelerRepository = spelerRepository;
    }

    // Speler aanmaken
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Speler createSpeler(@RequestBody Speler speler) {
        return spelerRepository.save(speler);
    }


}
