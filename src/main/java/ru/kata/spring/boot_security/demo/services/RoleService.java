package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;

public interface RoleService {
    void create(String name);

    Role get();
}