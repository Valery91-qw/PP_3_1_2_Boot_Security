package ru.kata.spring.boot_security.demo.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @Column(name = "name", unique = true)
   private String name;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "age")
   private int age;

   @Column(name = "password")
   private String password;

   @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
   @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))

   private List<Role> roles = new ArrayList<>();

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

   public void setPassword(String password) {
      this.password = password;
   }

   public List<Role> getRoles() {
      return roles;
   }

   public void setRoles(List<Role> roles) {
      this.roles = roles;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      List<GrantedAuthority> roles = new ArrayList<>();
      List<Role> userRoles = this.getRoles();

      for (var userRole : userRoles) {
         roles.add(new SimpleGrantedAuthority(userRole.getName()));
      }

      return roles;
   }

   @Override
   public String getPassword() {
      return this.password;
   }

   @Override
   public String getUsername() {
      return this.getName();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
}