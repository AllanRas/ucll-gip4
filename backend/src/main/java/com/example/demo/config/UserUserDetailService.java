package com.example.demo.config;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import com.example.demo.dto.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


@Service("UserUserDetailService")
public class UserUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        System.out.println(s);
        User user = userRepository.findFirstByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found",s)));
        return new UserPrincipal(user);
    }

    public UserDetails Login(UserLoginDTO userLoginDTO) throws Exception {

        UserDetails user = loadUserByUsername(userLoginDTO.getUsername());
        if(!passwordEncoder.matches(userLoginDTO.getPassword(),user.getPassword())){
            throw new Exception(String.format("User %s not found",userLoginDTO.getUsername()));
        }

        System.out.println(user.toString());
        return user;
    }


    public void Logout() {
        
    }
}
