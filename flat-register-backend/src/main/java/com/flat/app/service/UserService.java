package com.flat.app.service;

import java.util.List;

import com.flat.app.entity.User;
import com.flat.app.exception.NoUsersFoundException;
import com.flat.app.exception.UserExistsException;
import com.flat.app.exception.UserNotFoundException;

public interface UserService {

	public List<User> getAllUsers() throws NoUsersFoundException;

	public User registerUser(User user) throws UserExistsException;

	public User getUserById(String username) throws UserNotFoundException;

	public User updateOwnerStatus(String username, User user) throws UserNotFoundException;

}
