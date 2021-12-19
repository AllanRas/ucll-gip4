package com.example.demo.web;
    
import com.example.demo.Services.SpelerService;
import com.example.demo.config.UserPrincipal;
import com.example.demo.domain.User;
import com.example.demo.dto.CreateSpelerDTO;
import com.example.demo.dto.SpelerDTO;
import com.example.demo.dto.SpelerSimpleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/spelers")
@CrossOrigin("http://localhost:3000/")
public class SpelerResource {

    private final SpelerService spelerService;

    public SpelerResource(SpelerService spelerService){
        this.spelerService = spelerService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public SpelerDTO createSpeler(@RequestBody CreateSpelerDTO createSpelerDTO) {
        return spelerService.createSpeler(createSpelerDTO);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<SpelerDTO> getAllSpelers(){
        return spelerService.getAllSpelers();
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER','SPELER')")
    @GetMapping(value = "/{id}/getOne")
    public ResponseEntity<SpelerDTO> getById(@PathVariable("id") long id){

        // Get the logged in user
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isSpelerRole = userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SPELER"));
        boolean isManagerRole = userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGER"));

        SpelerDTO spelerDTO = new SpelerDTO();

        if(isManagerRole){
            spelerDTO = spelerService.getById(id);
        }

        // check if user is in SPELER role
        if(isSpelerRole){
            spelerDTO = spelerService.getByIdAndUser(id, userPrincipal);

        }
        return new ResponseEntity<SpelerDTO>(spelerDTO, HttpStatus.ACCEPTED);
    }

    //Verwijderen
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/delete")
    public SpelerDTO inActiveSpeler(@PathVariable("id") long id){
        return spelerService.inActiveSpeler(id);
    }

    //Gegevens wijzigen van een speler
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER','SPELER')")
    @PutMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SpelerDTO> updateSpeler(@PathVariable("id") long id, @RequestBody SpelerDTO spelerDTO){

        System.out.println("Start update proces speler");

        // Get the logged in user
        UserPrincipal userPrincipal = (UserPrincipal)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isSpelerRole = userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SPELER"));

        SpelerDTO getSpeler = spelerService.getById(id);

        // check if user is in SPELER role
        if(isSpelerRole){
            User ingelogdeUser = userPrincipal.getUser();
            // check if logged in user id equals to get speler user id if not return forbidden
            if(!(ingelogdeUser.getId() == getSpeler.getUserDTO().getId())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        SpelerDTO updateSpeler = spelerService.updateSpeler(id, spelerDTO);

        return new ResponseEntity<SpelerDTO>(updateSpeler, HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER','SPELER')")
    @GetMapping("/getAllSimple")
    public List<SpelerSimpleDTO> getAllSpelersSimple(){
        return spelerService.getAllSpelersSimple();
    }
}
