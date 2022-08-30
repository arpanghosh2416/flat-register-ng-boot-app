package com.flat.app.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flat.app.entity.User;
import com.flat.app.exception.NoUsersFoundException;
import com.flat.app.exception.UserNotFoundException;
import com.flat.app.repository.UserRepository;
import com.flat.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() throws NoUsersFoundException {
		List<User> users = userRepository.findAll();

		if (users.size() > 0)
			return users;

		throw new NoUsersFoundException("No users exist till now");
	}

	@Override
	public User registerUser(User user) {
		user.setUserRole("ROLE_" + user.getUserRole());
		User _user = userRepository.save(user);
		return _user;
	}

	@Override
	public User getUserById(String username) throws UserNotFoundException {
		User user = userRepository.findById(username)
				.orElseThrow(() -> new UserNotFoundException("No user found with username: " + username));
		return user;
	}

	@Override
	public User updateOwnerStatus(String username, User user) throws UserNotFoundException {
		User _user = this.getUserById(username);
		_user.setOwnerStatus(user.getOwnerStatus());
		return userRepository.save(_user);
	}

}
