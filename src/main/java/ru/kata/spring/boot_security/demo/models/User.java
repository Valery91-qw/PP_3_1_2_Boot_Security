package ru.kata.spring.boot_security.demo.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @Column(name = "name")
   private String name;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "age")
   private int age;

   @Column(name = "password")
   private String password;

   @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
   @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))

   private List<Roles> roles = new ArrayList<>();

   public User() {
   }

   public User(String name, String lastName, int age, String password) {
      this.name = name;
      this.lastName = lastName;
      this.age = age;
      this.password = password;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      this.age = age;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public List<Roles> getRoles() {
      return roles;
   }

   public void setRoles(List<String> roles) {
      List<Roles> modelRoles = new ArrayList<>();
      for (String role : roles) {
         modelRoles.add(new Roles(role));
      }
      this.roles = modelRoles;
   }
}