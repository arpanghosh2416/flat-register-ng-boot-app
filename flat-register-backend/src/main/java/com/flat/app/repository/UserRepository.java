package com.flat.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flat.app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	public Optional<User> findByUsernameAndPassword(String username, String password);

}
