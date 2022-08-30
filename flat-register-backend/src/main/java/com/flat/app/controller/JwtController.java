package com.flat.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flat.app.entity.User;
import com.flat.app.exception.UserNotFoundException;
import com.flat.app.jwt.JwtRequest;
import com.flat.app.jwt.JwtResponse;
import com.flat.app.jwt.JwtUtil;
import com.flat.app.service.CustomUserDetailsService;
import com.flat.app.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user/auth")
public class JwtController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@GetMapping("/")
	public String test() {
		String msg = "JWT-REST-API server is running";
		return msg;
	}

	@PostMapping("/login")
	public ResponseEntity<?> generateToken(@RequestBody @Valid JwtRequest jwtRequest) throws UserNotFoundException {
		String username = jwtRequest.getUsername();
		String password = jwtRequest.getPassword();

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			System.out.println("----- " + e.getMessage() + " -----");
			return ResponseEntity.status(401).body("Bad Credential");
		}

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
		User user = userService.getUserById(username);
		String jwt = jwtUtil.generateToken(userDetails);
		System.out.println("----- JWT: " + jwt + " -----");
		return ResponseEntity.status(201).body(JwtResponse.builder().user(user).jwt(jwt).build());
	}

}
