package ru.kata.spring.boot_security.demo.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public AuthProviderImpl(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();

        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(userName);

        String password = (String) authentication.getCredentials().toString();

        if(!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("the password is incorrect");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList()); 
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
    
}
