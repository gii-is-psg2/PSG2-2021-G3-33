package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.AdoptionApplications;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdoptionController {

	private static final String VIEWS_ADOPTION_CREATE_FORM = "adoptionApplications/createAdoptionForm";
	
	private final PetService	petService;
	private final OwnerService	ownerService;
	private final AdoptionApplicationService adoptionApplicationService;

	@Autowired
	public AdoptionController(final PetService petService, final OwnerService ownerService, AdoptionApplicationService adoptionApplicationService) {
		this.petService = petService;
		this.ownerService = ownerService;
		this.adoptionApplicationService = adoptionApplicationService;
	}
	
	@GetMapping("/petsInAdoption")
	public String showPetsInAdoption(Map<String, Object> model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Pet> pets = this.petService.findPetsInAdoption();
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
		model.put("pets", pets);
		
		return "pets/petsInAdoption";
	}
	
	@GetMapping("/petsInAdoption/adopt={petId}")
	public String initAdoptionForm(@PathVariable("petId") int petId, final Map<String, Object> model) {
		final AdoptionApplications application = new AdoptionApplications();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if(principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		}else {
			username = principal.toString();
		}
		Owner applicantOwner = this.ownerService.findOwnerByUsername(username);
		Pet pet = this.petService.findPetById(petId);
		
		application.setOwner(applicantOwner);
		application.setPet(pet);
		model.put("application", application);
		return VIEWS_ADOPTION_CREATE_FORM;
	}
	
	@PostMapping(value = "/petsInAdoption/adopt={petId}")
	public String processAdoptionForm(@Valid final AdoptionApplications application, @PathVariable("petId") int petId, final Map<String, Object> model,BindingResult result) {
		if(result.hasErrors()) {
			model.put("application", application);
			return VIEWS_ADOPTION_CREATE_FORM;
		}else {
			Pet pet = this.petService.findPetById(petId);
			System.out.println("mascota: " + application.getPet().getName());
			application.setDate(LocalDate.now());
			pet.addAdoptionApplication(application);
			this.adoptionApplicationService.saveAdoptionApplication(application);
			return "redirect:/owners/" + application.getOwner().getId();
		}
	}
	
	@GetMapping("/petsInAdoption/{petId}/{applicationId}/cancel")
	public String cancelAdoptionApplication(@PathVariable("petId") int petId, @PathVariable("applicationId") int applicationId,
			Map<String, Object> model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if(principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		}else {
			username = principal.toString();
		}
		Owner owner = this.ownerService.findOwnerByUsername(username);
		Pet pet = this.petService.findPetById(petId);
		AdoptionApplications ap = this.adoptionApplicationService.findApplicationById(applicationId);
		owner.removeAdoptionApplication(ap);
		pet.removeAdoptionApplication(ap);
		this.adoptionApplicationService.deleteAdoptionApplication(ap);

		model.put("owner", owner);
		return "redirect:/owners/" + owner.getId();
	}
	
	@GetMapping("/adoptionRequests/{petId}")
	public String showAdoptionRequests(@PathVariable("petId") int petId, Map<String, Object> model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if(principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		}else {
			username = principal.toString();
		}
		Owner owner = this.ownerService.findOwnerByUsername(username);
		Pet pet = this.petService.findPetById(petId);
		model.put("owner", owner);
		model.put("pet", pet);
		return "adoptionApplications/adoptionApplicationsList";
	}
	
	@GetMapping("/adoptionRequests/{petId}/{applicationId}/reject")
	public String rejectAdoptionRequest(@PathVariable("petId") int petId,@PathVariable("applicationId") int applicationId,Map<String, Object> model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		String autoridad;
		String view = "redirect:/oups";
		if(principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			autoridad = ((UserDetails)principal).getAuthorities().iterator().next().toString();
		}else {
			username = principal.toString();
			autoridad = principal.toString();
		}
		if(autoridad.equals("owner")) {
			Pet pet = this.petService.findPetById(petId);
			AdoptionApplications application = this.adoptionApplicationService.findApplicationById(applicationId);
			Owner owner = this.ownerService.findOwnerByUsername(username);
			if(pet.getOwner().equals(owner)) {
				Owner applicantOwner = application.getOwner();
				applicantOwner.removeAdoptionApplication(application);
				pet.removeAdoptionApplication(application);
				this.adoptionApplicationService.deleteAdoptionApplication(application);
				view = "redirect:/adoptionRequests/{petId}";
				model.put("pet", pet);
			}
		}
		return view;
	}
	
	@GetMapping("/adoptionRequests/{petId}/{applicationId}/accept")
	public String acceptAdoptionRequest(@PathVariable("petId") int petId,@PathVariable("applicationId") int applicationId,Map<String, Object> model) throws DataAccessException, DuplicatedPetNameException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		String autoridad;
		String view = "redirect:/oups";
		
		if(principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			autoridad = ((UserDetails)principal).getAuthorities().iterator().next().toString();
		}else {
			username = principal.toString();
			autoridad = principal.toString();
		}
		if(autoridad.equals("owner")) {
			Pet pet = this.petService.findPetById(petId);
			AdoptionApplications application = this.adoptionApplicationService.findApplicationById(applicationId);
			Owner owner = this.ownerService.findOwnerByUsername(username);
			if(pet.getOwner().equals(owner)) {
				Owner applicantOwner = application.getOwner();
				owner.removePet(pet);
				applicantOwner.addPet(pet);
				pet.setStatus(false);
				this.petService.savePet(pet);
				applicantOwner.removeAdoptionApplication(application);
				
				for(AdoptionApplications ap : pet.getApplications()) {
					applicantOwner = ap.getOwner();
					applicantOwner.removeAdoptionApplication(ap);
					pet.removeAdoptionApplication(ap);
					this.adoptionApplicationService.deleteAdoptionApplication(ap);
				}
				view = "redirect:/owners/" + owner.getId();
				model.put("pet", pet);
			}
		}
		return view;
	}
}
