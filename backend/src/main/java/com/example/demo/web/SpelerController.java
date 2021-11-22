package com.example.demo.web;


import com.example.demo.dao.SpelerRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.Speler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Spelers")
public class SpelerController {

    private final SpelerRepository spelerRepository;

    public SpelerController(SpelerRepository spelerRepository){
        this.spelerRepository = spelerRepository;
    }

    // Speler aanmaken
    @PostMapping
    public Speler createSpeler(@RequestBody Speler user) {
        return spelerRepository.save(user);
    }

}
