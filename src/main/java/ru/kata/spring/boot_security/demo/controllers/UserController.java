package ru.kata.spring.boot_security.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String printWelcome(Model model) {
		userService.create(new User("user2", "user", 0, "user"));
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_USER");
		userService.setUserRoles((long) 1, roles);
		return "index";
	}

	@GetMapping(value = "/new")
	public String newUser(@ModelAttribute("user") User user) {
		return "/new";
	}

	@PostMapping()
	public String addUser(@ModelAttribute("user") User user) {
		userService.create(user);
		return "redirect:/";
	}
	

	// @GetMapping(value = "/{id}/update")
	// public String updateUser(@PathVariable("id") long id, Model model) {
	// 	User user = userService.get(id);
	// 	model.addAttribute("user", user);
	// 	return "/update";
	// }

	// @PostMapping(value = "/{id}")
	// public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {
	// 	userService.update(user);
	// 	return "redirect:/";
	// }

	// @GetMapping(value = "/{id}/delete")
	// public String deleteUser(@PathVariable("id") long id) {
	// 	User user = userService.get(id);
	// 	userService.delete(user);
	// 	return "redirect:/";
	// }
}