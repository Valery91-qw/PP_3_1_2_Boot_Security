package ru.kata.spring.boot_security.demo.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String printWelcome() {
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
	
	@GetMapping("/user")
	public String getMethodName(Authentication authentication, Model model) {
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		User user = userDetailsImpl.getUser();
		model.addAttribute("user", user); 
		return "/user";
	}

	@GetMapping("/admin")
	public String getMethodName() {
		return "/admin";
	}
	
	
	@GetMapping("/admin/users")
	public String getMethodName(Model model, Authentication authentication) {
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		User user = userDetailsImpl.getUser();
		List<User> users = userService.listUsers();
		model.addAttribute("users", users);
		model.addAttribute("currentUser", user.getId());
		return "/users";
	}
	
	@GetMapping(value = "/admin/users/{id}/update")
	public String updateUser(@PathVariable("id") long id, Model model) {
		User user = userService.get(id);
		model.addAttribute("user", user);
		return "/update";
	}

	@PostMapping(value = "/admin/users/{id}/update")
	public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {
		userService.update(user);
		return "redirect:/admin/users";
	}

	@GetMapping(value = "/admin/users/{id}/delete")
	public String deleteUser(@PathVariable("id") long id) {
		User user = userService.get(id);
		userService.delete(user);
		return "redirect:/admin/users";
	}
}