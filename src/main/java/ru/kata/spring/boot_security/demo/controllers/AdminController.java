package ru.kata.spring.boot_security.demo.controllers;

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
    public String addUser(@ModelAttribute("user") User user, @RequestBody MultiValueMap<String, String> formData) {
        List<String> role = formData.get("role");
        user.setRoles(role);
        userService.create(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/{id}/edit")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User user,
            @RequestBody MultiValueMap<String, String> formData) {
        List<String> role = formData.get("role");
        user.setRoles(role);
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/{id}/delete")
    public String deleteUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {
        userService.delete(user);
        return "redirect:/admin";
    }
}
