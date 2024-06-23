package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

   private final UserDao userDao;
   private final RoleDao roleDao;
   private final PasswordEncoder passwordEncoder;

   public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
      this.userDao = userDao;
      this.passwordEncoder = passwordEncoder;
      this.roleDao = roleDao;
   }

   @Override
   @Transactional
   public void create(User user) throws RuntimeException {
      if (this.validateUserName(user)) {
         this.userDao.add(this.encodePassword(user));
      } else {
         throw new RuntimeException("The field should be unique");
      }
   }

   @Override
   public User get(long id) {
      return this.userDao.get(id);
   }

   @Override
   @Transactional
   public void update(User user) throws RuntimeException {
      this.userDao.update(this.encodePassword(user));
   }

   @Override
   @Transactional
   public void delete(User user) {
      this.userDao.delete(user);
   }

   @Override
   public List<User> listUsers() {
      return this.userDao.listUsers();
   }

   @Override
   @Transactional
   public void setUserRoles(Long userId, List<Role> roles) {
      this.userDao.setUserRoles(userId, roles);
   }

   private User encodePassword(User user) {
      String pass = user.getPassword();
      user.setPassword(passwordEncoder.encode(pass));
      return user;
   };

   private boolean validateUserName(User user) {
      return this.userDao.loadUserByUsername(user.getUsername()).isEmpty();
   }
}
