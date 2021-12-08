package com.example.demo.web;

import com.example.demo.config.UserUserDetailService;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserLoginDTO;
import io.swagger.v3.oas.annotations.headers.Header;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;


@RestController
@RequestMapping("/login")
@CrossOrigin("http://localhost:3000")
public class BasicAuthenticationResource {

    private final UserUserDetailService userUserDetailService;

    public BasicAuthenticationResource(UserUserDetailService userUserDetailService){
        this.userUserDetailService = userUserDetailService;
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/user")
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    public UserDetails Authentication(@RequestBody UserLoginDTO userLoginDTO) throws Exception {
        UserDetails user = userUserDetailService.Login(userLoginDTO);
        return user;
    }


}
