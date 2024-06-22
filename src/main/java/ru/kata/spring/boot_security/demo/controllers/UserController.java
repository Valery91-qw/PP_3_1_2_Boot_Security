package ru.kata.spring.boot_security.demo.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;

// TODO Handling exeptions after reduce the controller
@Controller
@RequestMapping("/")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/create")
	public String createUser() {
		User user = new User("admin", "admin", 0, "admin");
		String[] roles = { "ROLE_ADMIN", "ROLE_USER" };
		List<String> role = Arrays.asList(roles);
		user.setRoles(role);
		userService.create(user);
		return "redirect:/admin";
	}

	@PostMapping()
	public String addUser(@ModelAttribute("user") User user, @RequestBody MultiValueMap<String, String> formData) {
		List<String> role = formData.get("role");
		user.setRoles(role);
		userService.create(user);
		return "redirect:/admin";
	}

	@GetMapping("/user")
	public String getMethodName(Authentication authentication, Model model) {
		User user = (User) authentication.getPrincipal();
		model.addAttribute("user", user);
		return "/index";
	}

	@GetMapping("/admin")
	public String getMethodName(Model model, Authentication authentication, @ModelAttribute("user") User user) {
		User currentUser = (User) authentication.getPrincipal();
		List<User> users = userService.listUsers();
		model.addAttribute("users", users);
		model.addAttribute("user", currentUser);
		model.addAttribute("currentId", currentUser.getId());
		return "/index";
	}

	@PostMapping(value = "/admin/{id}/edit")
	public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User user,
			@RequestBody MultiValueMap<String, String> formData) {
		List<String> role = formData.get("role");
		user.setRoles(role);
		userService.update(user);
		return "redirect:/admin";
	}

	@PostMapping(value = "/admin/{id}/delete")
	public String deleteUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {
		userService.delete(user);
		return "redirect:/admin";
	}
}