package ru.kata.spring.boot_security.demo.controllers;

// import hiber.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

// import java.util.ArrayList;
// import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String printWelcome(Model model) {
		userService.create(new User("user1", "user", 0, "user"));
		return "index";
	}

	// @GetMapping(value = "/new")
	// public String newUser(@ModelAttribute("user") User user) {
	// 	return "/new";
	// }

	// @PostMapping()
	// public String addUser(@ModelAttribute("user") User user) {
	// 	userService.create(user);
	// 	return "redirect:/";
	// }

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