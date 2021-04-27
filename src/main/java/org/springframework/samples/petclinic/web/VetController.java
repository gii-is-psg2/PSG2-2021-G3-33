/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.FormVetType;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private static final String		VIEWS_VET_CREATE_FORM	= "vets/createVetForm";
	private static final String		VIEWS_VET_EDIT_FORM		= "vets/updateVetForm";

	private final VetService		vetService;

	private final SpecialtyService	specialtyService;
	
	private final OwnerService ownerService;


	@Autowired
	public VetController(final VetService vetService, final SpecialtyService specialtyService, OwnerService ownerService) {
		this.vetService = vetService;
		this.specialtyService = specialtyService;
		this.ownerService = ownerService;
	}

	@GetMapping(value = {
		"/vets"
	})
	public String showVetList(final Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String autoridad;
		String username;
		if(principal instanceof UserDetails) {
			autoridad = ((UserDetails)principal).getAuthorities().iterator().next().toString();
			username = ((UserDetails)principal).getUsername();
		}else {
			autoridad = principal.toString();
			username = principal.toString();
		}
		if(autoridad.equals("owner")) {
			Owner owner = this.ownerService.findOwnerByUsername(username);
			model.put("owner", owner);
		}
		
		final Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(path = "/vets/new")
	public String createVet(final Map<String, Object> model) {
		final FormVetType vet = new FormVetType();
		model.put("vet", vet);
		return VetController.VIEWS_VET_CREATE_FORM;
	}

	@PostMapping(value = "/vets/new")
	public String processCreationForm(@ModelAttribute(name = "vet") @Valid final FormVetType vetType, final BindingResult result) {
		if (result.hasErrors()) {
			return VetController.VIEWS_VET_CREATE_FORM;
		} else {

			this.vetService.save(vetType);

			return "redirect:/vets";
		}

	}

	@ModelAttribute("specialtiesTypes")
	public Collection<Specialty> getAllSpecialties() {
		return this.specialtyService.findSpecialtyTypes();
	}

	@GetMapping(value = "/vets/{vetId}/edit")
	public String initUpdateVetForm(@PathVariable("vetId") final int vetId, final Model model) {
		final Vet vet = this.vetService.findById(vetId);
		final FormVetType formVetType = new FormVetType();
		formVetType.setFirstName(vet.getFirstName());
		formVetType.setLastName(vet.getLastName());
		formVetType.setSpecialties(vet.getSpecialties());
		model.addAttribute("vet", formVetType);
		return VetController.VIEWS_VET_EDIT_FORM;
	}

	@PostMapping(value = "/vets/{vetId}/edit")
	public String processUpdateVetForm(@ModelAttribute(name = "vet") @Valid final FormVetType vetType, final BindingResult result, @PathVariable("vetId") final int vetId) {
		if (result.hasErrors()) {
			return VetController.VIEWS_VET_EDIT_FORM;
		} else {
			this.vetService.modify(vetType, vetId);
			return "redirect:/vets";
		}
	}

	@GetMapping("/vets/{vetId}/delete")
	public String deleteVet(@PathVariable("vetId") final int vetId, final Map<String, Object> model) {
		final Vet vet = this.vetService.findVetById(vetId);
		this.vetService.deleteVet(vet);
		return "redirect:/vets";

	}
}
