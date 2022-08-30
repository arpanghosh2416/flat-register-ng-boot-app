package com.flat.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flat.app.entity.User;
import com.flat.app.exception.NoUsersFoundException;
import com.flat.app.exception.UserNotFoundException;
import com.flat.app.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/get-all-users")
	public ResponseEntity<?> getAllUsers() throws NoUsersFoundException {
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
		User _user = userService.registerUser(user);
		return ResponseEntity.status(201).body(_user);
	}

	@PutMapping("/update-user/{username}")
	public ResponseEntity<?> updateOwnerStatus(@PathVariable String username, @RequestBody User user)
			throws UserNotFoundException {
		User _user = userService.updateOwnerStatus(username, user);
		return ResponseEntity.status(201).body(_user);
	}

}
