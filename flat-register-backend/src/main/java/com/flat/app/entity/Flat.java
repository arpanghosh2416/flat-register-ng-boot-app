package com.flat.app.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "flat")
public class Flat {

	@Id
	@GenericGenerator(name = "flat_id_generator", strategy = "com.flat.app.generator.FlatIdGenerator")
	@GeneratedValue(generator = "flat_id_generator")
	@Column(name = "flat_id")
	private Long flatId;

	@Column(name = "storey_number")
	@NotNull(message = "Every flat should have a storey number")
	private Long storeyNumber;

	@Column(name = "living_status")
	@NotNull(message = "Living status should be true or false")
	private boolean livingStatus;

	@Column(name = "sold_out")
	private boolean soldOut;

	private Long price;

	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_id", referencedColumnName = "owner_id")
	private Owner owner;

}
