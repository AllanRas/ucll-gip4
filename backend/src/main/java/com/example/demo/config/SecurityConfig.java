package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //basic http security & alleen authenticated requests worden authorized, moet nog aangepast worden.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                    .and()
            .authorizeRequests()
                    .antMatchers(HttpMethod.POST,"/spelers").hasRole("MANAGER")
                    .and()
            .formLogin()
                    .defaultSuccessUrl("/", true)
                    .permitAll()
                    .and()
            .csrf().disable()
            .logout()
                    .logoutSuccessUrl("/");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("speler").password(BCryptPasswordEncoder().encode("speler")).roles("SPELER")
                .and()
                .withUser("manager").password(BCryptPasswordEncoder().encode("manager")).roles("MANAGER")
                .and()
                .withUser("admin").password(BCryptPasswordEncoder().encode("admin")).roles("SPELER","MANAGER","ADMIN");
    }

    @Bean
    public BCryptPasswordEncoder BCryptPasswordEncoder(){ return new BCryptPasswordEncoder(); }

}
