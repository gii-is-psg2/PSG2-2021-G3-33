package org.springframework.samples.petclinic.util;

import org.springframework.samples.petclinic.model.FormVetType;

public class VetValidator {

	// el siguiente regex no permite número ni caracteres especiales, solo letras del alfabeto(incluida la ñ) con sus tildes.
	private static final String regex = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]*)*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$";
	private VetValidator() {
		new VetValidator();
	}
	
	public static boolean validateNameVet(FormVetType vet) {
		boolean res = false;
		if(!vet.getFirstName().matches(regex)) {
			res = true;
		}
		
		return res;
	}
	
	public static boolean validateLastNameVet(FormVetType vet) {
		boolean res = false;
		if(!vet.getLastName().matches(regex)) {
			res = true;
		}
		
		return res;
	}
}
