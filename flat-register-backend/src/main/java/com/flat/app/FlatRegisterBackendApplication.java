package com.flat.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.flat.app.entity.Flat;
import com.flat.app.entity.User;
import com.flat.app.exception.UserExistsException;
import com.flat.app.repository.FlatRepository;
import com.flat.app.service.UserService;

@SpringBootApplication
public class FlatRegisterBackendApplication {

	@Autowired
	private UserService userService;

	@Autowired
	private FlatRepository flatRepository;

	public static void main(String[] args) {
		SpringApplication.run(FlatRegisterBackendApplication.class, args);
	}

	private long getRandomNumber(long min, long max) {
		return (long) ((Math.random() * (max - min)) + min);
	}

	@PostConstruct
	public void dummy() throws UserExistsException {
		flatRepository.save(Flat.builder().storeyNumber(0L).build());

		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 6; j++) {
				Long randomPrice = this.getRandomNumber(450000L, 10000000L);
				Flat flat = Flat.builder().storeyNumber((long) i).price(randomPrice).livingStatus(false).soldOut(false)
						.build();
				flatRepository.save(flat);
			}
		}
		System.out.println("--- 24 Flats are saved ---");

		User user = User.builder().username("admin123").password("Admin@123").userRole("ADMIN").build();
		userService.registerUser(user);
		System.out.println("--- Admin Created ---");
	}

}
