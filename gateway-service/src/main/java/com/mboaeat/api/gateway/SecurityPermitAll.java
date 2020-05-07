package com.mboaeat.api.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;


//@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityPermitAll {

   /* @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");
        http.httpBasic().disable()
                .formLogin().disable()
                .authorizeRequests()
                .antMatchers("/account/**", "/customer/**").permitAll()
                .anyRequest().permitAll()
                .and().csrf().disable();
    }

    */
}
