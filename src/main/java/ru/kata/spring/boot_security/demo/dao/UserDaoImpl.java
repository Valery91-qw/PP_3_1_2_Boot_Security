package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public void add(User user) {
      entityManager.persist(user);
   }

   @Override
   public User get(Long id) {
      User user = entityManager.find(User.class, id);
      return user;
   }

   @Override
   public void update(User user) {
      entityManager.merge(user);
   }

   @Override
   public void delete(User user) {
      user = entityManager.merge(user);
      entityManager.remove(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      List<User> users = entityManager.createQuery("from User").getResultList();
      return users;
   }

   @Override
   public Optional<User> loadUserByUsername(String username) {
      TypedQuery<User> query = entityManager.createQuery("from User WHERE name = :username", User.class);
      query.setParameter("username", username);
      User user;
      try {
         user = query.setMaxResults(1).getSingleResult();
      } catch (Exception e) {
         user = null;
      }
      return Optional.ofNullable(user);
   }

   @Override
   public void setUserRoles(Long userId, List<String> roles) {
      User user = get(userId);
      user.setRoles(roles);
      update(user);
   }
}
