package com.flat.app.jwt;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

	@NotNull(message = "Username shouldn't be null")
//	@Size(min = 8, max = 50, message = "Should be 8 to 50 characters")
	private String username;

	@NotNull(message = "Password shouldn't be null")
//	@Pattern(regexp = "((?=.*[@!#$%])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,30})", message = "Should be 8 to 30 characters and must contain 1 uppercase, lowercase, number and special character")
	private String password;

}
