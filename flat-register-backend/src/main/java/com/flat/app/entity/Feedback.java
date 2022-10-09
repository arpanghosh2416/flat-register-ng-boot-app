package com.flat.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "feedbacks")
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "feedback_id")
	private Long feedbackId;

	@Email(message = "Email is not valid")
	@NotNull(message = "Email cannot be null")
	private String email;

	@NotBlank(message = "Message cannot be null or empty")
	private String message;

}
