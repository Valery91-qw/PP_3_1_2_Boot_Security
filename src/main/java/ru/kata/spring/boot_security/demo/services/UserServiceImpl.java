package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

   private final UserDaoImpl userDaoImpl;
   private final PasswordEncoder passwordEncoder;

   public UserServiceImpl(UserDaoImpl userDaoImpl, PasswordEncoder passwordEncoder) {
      this.userDaoImpl = userDaoImpl;
      this.passwordEncoder = passwordEncoder;
   }

   @Override
   @Transactional
   public void create(User user) {
      userDaoImpl.add(this.encodePassword(user));
   }

   @Override
   public User get(long id) {
      return userDaoImpl.get(id);
   }

   @Override
   @Transactional
   public void update(User user) {
      this.userDaoImpl.update(this.encodePassword(user));
   }

   @Override
   @Transactional
   public void delete(User user) {
      this.userDaoImpl.delete(user);
   }

   @Override
   public List<User> listUsers() {
      return this.userDaoImpl.listUsers();
   }

   @Override
   public void setUserRoles(Long userId, List<String> roles) {
      this.userDaoImpl.setUserRoles(userId, roles);
   }

   private User encodePassword(User user) {
      String pass = user.getPassword();
      user.setPassword(passwordEncoder.encode(pass));
      return user;
   };
}
