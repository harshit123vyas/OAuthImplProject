package com.springsecurity.oauth2impl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.oauth2impl.entity.AuthUser;
import com.springsecurity.oauth2impl.entity.Role;
import com.springsecurity.oauth2impl.repository.AuthUserRepository;

@RestController
public class OauthController {

	@Autowired
	private AuthUserRepository userRepository;

	@GetMapping("/")
	public String home() {
		return "permit to all ";
	}

	@GetMapping("/secured")
	public String secured() {
		return " Secured! not permit to all ";
	}

	@GetMapping("/user")
	public AuthUser getUser(@AuthenticationPrincipal OAuth2User principal) {
		String email = principal.getAttribute("email");

		// Load the user from the database using the email
		AuthUser user = userRepository.findByEmail(email).get();
		if (user == null) {
			{
				Role role = new Role();
				role.setRoleName("USER");
				role.setDescription("user can access..");
				user.setRole(role);
				userRepository.save(user);

			}
		}
		return user;
	}

}
