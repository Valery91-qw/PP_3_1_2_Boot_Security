package ru.kata.spring.boot_security.demo.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
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
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		User user = userDetailsImpl.getUser();
		model.addAttribute("user", user);
		return "/index";
	}

	@GetMapping("/admin")
	public String getMethodName(Model model, Authentication authentication, @ModelAttribute("user") User user) {
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		User currentUser = userDetailsImpl.getUser();
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