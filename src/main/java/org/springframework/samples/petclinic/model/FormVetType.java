
package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.validation.constraints.NotBlank;

public class FormVetType {

	private int						id;

	@NotBlank
	private String					firstName;

	@NotBlank
	private String					lastName;

	private Collection<Specialty>	specialties;


	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Collection<Specialty> getSpecialties() {
		return this.specialties;
	}

	public void setSpecialties(final Collection<Specialty> specialties) {
		this.specialties = specialties;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

}
