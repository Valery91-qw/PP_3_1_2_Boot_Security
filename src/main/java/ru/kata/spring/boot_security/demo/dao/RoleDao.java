package ru.kata.spring.boot_security.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.kata.spring.boot_security.demo.models.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
