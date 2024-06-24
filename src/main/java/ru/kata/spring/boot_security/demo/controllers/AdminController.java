package ru.kata.spring.boot_security.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAdminPage(Model model, Authentication authentication, @ModelAttribute("user") User user,
            @ModelAttribute("role") Role role) {
        User currentUser = (User) authentication.getPrincipal();
        List<User> users = userService.listUsers();
        List<Role> roles = roleService.getAllRole();

        model.addAttribute("users", users);
        model.addAttribute("user", currentUser);
        model.addAttribute("currentId", currentUser.getId());
        model.addAttribute("roles", roles);
        return "/index";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, @ModelAttribute("role") Role role) {
        String username = role.getName().split(",")[0];
        // Role currentRole = this.roleService.findByName(strings[1]);
        List<Role> roles = exstractRoles(role.getName());
        user.setName(username);
        user.setRoles(roles);
        this.userService.create(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/{id}/edit")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User user,
            @RequestBody MultiValueMap<String, String> formData) {
        String names = formData.get("role").toString();
        List<Role> roles = exstractRoles(names.substring(1, names.length() - 1));
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/{id}/delete")
    public String deleteUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {
        userService.delete(user);
        return "redirect:/admin";
    }

    private List<Role> exstractRoles(String names) {
        String[] allNames = names.split(",");
        List<Role> roles = new ArrayList<>();
        for (String name : allNames) {
            if (name.contains("ROLE_")) {
                roles.add(this.roleService.findByName(name.trim()));
            }
        }
        return roles;
    }
}
