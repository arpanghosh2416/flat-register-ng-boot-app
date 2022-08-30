package com.flat.app.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "flat_owner", uniqueConstraints = {
		@UniqueConstraint(columnNames = "owner_email"),
		@UniqueConstraint(columnNames = "phone_number")
})
public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "owner_id")
	private Long ownerId;

	@Column(name = "owner_name")
	@NotNull(message = "Owner name cannot be null")
	@Size(min = 3, max = 150, message = "Owner name should be minimum 3 and maximum 150 characters")
	private String ownerName;

	@Column(name = "owner_email")
	@Email(message = "Email is not valid")
	@NotNull(message = "Email cannot be null")
	private String ownerEmail;

	@Column(name = "phone_number")
	@NotNull(message = "Phone number cannot be null")
	@Pattern(regexp = "^\\d{10}$", message = "Phone number is not valid")
	private String phoneNumber;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<Flat> flats;

}
