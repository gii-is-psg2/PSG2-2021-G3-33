package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	private final OwnerService ownerService;

	@Autowired
	public WelcomeController(OwnerService ownerService, UserService userService, AuthoritiesService authoritiesService) {
		this.ownerService = ownerService;
	}
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  		String username;
  		String autoridad;
  		
  		if(principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			autoridad = ((UserDetails)principal).getAuthorities().iterator().next().toString();
  		}else {
  			username = principal.toString();
  			autoridad = principal.toString();
  		}
  		
  		if(autoridad.equals("owner")) {
  			Owner owner = this.ownerService.findOwnerByUsername(username);
  			model.put("owner", owner);
  		}
	    return "welcome";
	  }
}
