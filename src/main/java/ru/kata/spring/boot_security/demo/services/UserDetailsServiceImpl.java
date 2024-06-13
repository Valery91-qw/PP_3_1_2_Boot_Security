package ru.kata.spring.boot_security.demo.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;
import ru.kata.spring.boot_security.demo.models.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDaoImpl userDaoImpl;

    public UserDetailsServiceImpl(UserDaoImpl userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = this.userDaoImpl.loadUserByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with such name: " + username);
        }

        return new UserDetailsImpl(userOptional.get());
    }

}
