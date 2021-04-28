package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "adoptionApplications")
public class AdoptionApplications extends BaseEntity {

	@Column(name = "description")
	@NotNull
	private String description;
	
	@Column(name = "date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}
}
