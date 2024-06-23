package ru.kata.spring.boot_security.demo.dao;

import java.util.List;
import java.util.Optional;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

public interface UserDao {
   void add(User user);

   User get(Long id);

   void update(User user);

   void delete(User user);

   List<User> listUsers();

   Optional<User> loadUserByUsername(String username);

   void setUserRoles(Long userId, List<Role> roles);
}
