package com.springsecurity.oauth2impl.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.springsecurity.oauth2impl.entity.AuthUser;
import com.springsecurity.oauth2impl.entity.Role;
import com.springsecurity.oauth2impl.repository.AuthUserRepository;
import com.springsecurity.oauth2impl.repository.RoleRepository;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	@Autowired
    private  AuthUserRepository userRepository;
	
	@Autowired
    private  RoleRepository roleRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// Load user details from OAuth2 provider
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oauth2User = delegate.loadUser(userRequest);

		// Extract user attributes
		String email = oauth2User.getAttribute("email");
		String name = oauth2User.getAttribute("name");

		// Check if the user already exists
		Optional<AuthUser> optionalUser = userRepository.findByEmail(email);
		AuthUser authuser;
		if (optionalUser.isPresent()) {
			authuser = optionalUser.get();
		} else {
			// Create a new user if not exists
			authuser = new AuthUser();
			authuser.setEmail(email);
			authuser.setUserName(name);; // You can adjust username as needed
			authuser.setPassword(""); // You can set a dummy password or leave it blank
			// Set default role (e.g., ROLE_USER)
			Role role = roleRepository.findByRoleName("USER").get();
			authuser.setRole(role);
			userRepository.save(authuser);
		}

		return oauth2User;
	}
}