package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {

	@NotBlank
	@Column(name = "details")
	private String details;

	@NotNull
	@Column(name = "start_date_book")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate startDate;

	@NotNull
	@Column(name = "end_date_book")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate endDate;
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Pet getPet() {
		return this.pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
	
}
