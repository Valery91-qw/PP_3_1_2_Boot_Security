package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.User;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	@GetMapping()
	public String getMethodName(Authentication authentication, Model model) {
		User user = (User) authentication.getPrincipal();
		model.addAttribute("user", user);
		return "/index";
	}
}