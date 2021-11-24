package com.example.demo.config;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found",s)));
        return new UserPrincipal(user);
    }
}
