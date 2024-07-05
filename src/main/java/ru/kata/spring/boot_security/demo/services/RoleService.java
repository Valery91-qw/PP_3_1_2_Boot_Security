package ru.kata.spring.boot_security.demo.services;

import java.util.List;

import ru.kata.spring.boot_security.demo.models.Role;

public interface RoleService {
    void create(String name);

    Role findByName(String name);

    List<Role> getAllRole();
}