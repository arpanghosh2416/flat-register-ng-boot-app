package com.flat.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {

	@Id
	@Size(min = 8, max = 50, message = "Should be 8 to 50 characters")
	private String username;

	@NotNull(message = "Password shouldn't be null")
	@Pattern(regexp = "((?=.*[@!#$%])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,30})", message = "Should be 8 to 30 characters and must contain 1 uppercase, lowercase, number and special character")
	private String password;

	@Column(name = "owner_status")
	private long ownerStatus;

	@Column(name = "user_role")
	@NotBlank(message = "Role cannot be empty or null")
	private String userRole;

}
