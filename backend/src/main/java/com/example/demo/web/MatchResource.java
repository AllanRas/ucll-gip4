package com.example.demo.web;

import com.example.demo.Services.MatchService;
import com.example.demo.domain.Match;
import com.example.demo.dto.MatchDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchResource {

    private final MatchService matchService;

    public MatchResource(MatchService matchService) {
        this.matchService = matchService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public MatchDTO addMatch(@RequestBody MatchDTO matchDTO) {
        return matchService.addMatch(matchDTO);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<MatchDTO> getAllMatches(){
        return matchService.getAllMatches();
    }
}
