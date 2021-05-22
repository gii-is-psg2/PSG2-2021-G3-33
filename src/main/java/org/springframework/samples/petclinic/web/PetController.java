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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.AdoptionApplications;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.AdoptionApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

	private static final String	VIEWS_PETS_CREATE_OR_UPDATE_FORM	= "pets/createOrUpdatePetForm";

	private final PetService	petService;
	private final OwnerService	ownerService;

	private final AdoptionApplicationService adoptionApplicationService;

	@Autowired
	public PetController(final PetService petService, final OwnerService ownerService,AdoptionApplicationService adoptionApplicationService) {
		this.petService = petService;
		this.ownerService = ownerService;
		this.adoptionApplicationService = adoptionApplicationService;
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.petService.findPetTypes();
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") final int ownerId) {
		return this.ownerService.findOwnerById(ownerId);
	}

	/*
	 * @ModelAttribute("pet")
	 * public Pet findPet(@PathVariable("petId") Integer petId) {
	 * Pet result=null;
	 * if(petId!=null)
	 * result=this.clinicService.findPetById(petId);
	 * else
	 * result=new Pet();
	 * return result;
	 * }
	 */

	@InitBinder("owner")
	public void initOwnerBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("pet")
	public void initPetBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}

	@GetMapping(value = "/pets/new")
	public String initCreationForm(final Owner owner, final ModelMap model) {
		final Pet pet = new Pet();
		owner.addPet(pet);
		model.put("pet", pet);
		return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/pets/new")
	public String processCreationForm(final Owner owner, @Valid final Pet pet, final BindingResult result, final ModelMap model) {
		if (PetValidator.validateBirthDate(pet)) {
			result.rejectValue("birthDate", "birthDateError","La fecha debe ser anterior al dia de hoy");
		}
		if (result.hasErrors()) {
			model.put("pet", pet);
			return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				owner.addPet(pet);
				this.petService.savePet(pet);
			} catch (final DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable("petId") final int petId, final ModelMap model) {
		final Pet pet = this.petService.findPetById(petId);
		model.put("pet", pet);
		return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	/**
	 *
	 * @param pet
	 * @param result
	 * @param petId
	 * @param model
	 * @param owner
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/pets/{petId}/edit")
	public String processUpdateForm(@Valid final Pet pet, final BindingResult result, final Owner owner, @PathVariable("petId") final int petId, final ModelMap model) {
		if (PetValidator.validateBirthDate(pet)) {
			result.rejectValue("birthDate", "birthDateError","La fecha debe ser anterior al dia de hoy");
		}
        if (result.hasErrors()) {
			model.put("pet", pet);
			return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			final Pet petToUpdate = this.petService.findPetById(petId);
			BeanUtils.copyProperties(pet, petToUpdate, "id", "owner", "visits");
			try {
				this.petService.savePet(petToUpdate);
			} catch (final DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/owners/{ownerId}";
		}
	}

    	@GetMapping("/pets/{petId}/delete")
    	public String deletePet(@PathVariable("petId") int petId,Owner owner, Map<String,Object> model) {
    		Pet pet = this.petService.findPetById(petId);
    		owner.removePet(pet);
    		this.petService.deletePet(pet);
    		return "owners/ownerDetails";
    	}
    	
    	@GetMapping("/pets/{petId}/{visitId}/delete")
    	public String deleteVisit(@PathVariable("petId") int petId, @PathVariable("visitId") int visitId, Map<String,Object> model) {
    		Visit visit = this.petService.findVisitById(visitId);
    		Pet pet = this.petService.findPetById(petId);
    		pet.removeVisit(visit);
    		this.petService.deleteVisit(visit);
    		return "owners/ownerDetails";
    	}
    	
    	@GetMapping("/pets/{petId}/{status}")
    	public String changeDisponibility(@PathVariable("petId")int petId, @PathVariable("status") boolean status,@PathVariable("ownerId") int ownerId, Map<String,Object> model) throws DataAccessException, DuplicatedPetNameException {
    		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		String username;
    		
    		if(principal instanceof UserDetails) {
    			username = ((UserDetails)principal).getUsername();
    		}else {
    			username = principal.toString();
    		}
    		
    		Owner owner = this.ownerService.findOwnerByUsername(username);
    		Pet petToUpdate = this.petService.findPetById(petId);
    		
    		if(status == false) {
        		for(AdoptionApplications ap : petToUpdate.getApplications()) {
        			Owner applicantOwner = ap.getOwner();
        			applicantOwner.removeAdoptionApplication(ap);
    				petToUpdate.removeAdoptionApplication(ap);
    				this.adoptionApplicationService.deleteAdoptionApplication(ap);
    			}
    		}
       		petToUpdate.setStatus(status);
       		this.petService.savePet(petToUpdate);
       		model.put("owner", owner);

    		return "redirect:/owners/{ownerId}";
    	}
}
