package com.example.demo.config;

import com.example.demo.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    // haalt role van
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // true voor testing
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // true voor testing
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // true voor testing
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // true voor testing
    @Override
    public boolean isEnabled() {
        return true;
    }
}
