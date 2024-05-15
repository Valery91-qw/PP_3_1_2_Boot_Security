package ru.kata.spring.boot_security.demo.services;

import java.util.List;

import ru.kata.spring.boot_security.demo.models.User;

public interface UserService {
    void create(User user);
    User get(long id);
    void update(User user);
    void delete(User user);
    List<User> listUsers();
}
