package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.RoomValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RoomController {

	private static final String	VIEWS_ROOM_CREATE_OR_UPDATE_FORM = "rooms/createOrUpdateRoomForm";
	
	private final RoomService roomService;
	private final PetService petService;
	
	@Autowired
	public RoomController(RoomService roomService, PetService petService) {
		this.roomService = roomService;
		this.petService = petService;
	}
	
	@InitBinder
	public void setAllowedFields( WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("room")
	public Room loadPetWithHotel(@PathVariable("petId") int petId) {
		
		Pet pet = this.petService.findPetById(petId);
		Room room = new Room();
		pet.addRoom(room);
		return room;
	}
	
	@GetMapping(value = "/owners/*/pets/{petId}/rooms/new")
	public String initNewRoomForm(@PathVariable("petId") int petId, Map<String,Object> model) {
		return VIEWS_ROOM_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/rooms/new")
	public String processNewRoomForm(@Valid Room room, BindingResult result) {
		
		Pet pet = room.getPet();
		if(pet != null) {
			Collection<Room> rooms = this.roomService.findRoomsByPetId(pet.getId());
			
			if(room.getStartDate() != null && room.getEndDate() != null) {
				if(RoomValidator.validateDates(room)) {
					result.rejectValue("endDate", "startDateAfterEndDate", "La fecha de final de la estancia no puede ser anterior a la fecha de inicio de la misma");
				}
				if(RoomValidator.validatesDatesToday(room)) {
					result.rejectValue("startDate", "startDateBeforeTodayDate", "La fecha de inicio de la estancia no puede ser anterior a la fecha actual");
				}
				if(RoomValidator.validateRoomsOccupied(room, rooms)) {
					result.rejectValue("startDate","duplicatedRoom", "Ya hay una estancia para la mascota en esos dias");
				}
			}
			
		}
		if(result.hasErrors()) {
			return VIEWS_ROOM_CREATE_OR_UPDATE_FORM;
		}else {
			this.roomService.saveRoom(room);
			return "redirect:/owners/{ownerId}"; 
		}
	}
	
	@GetMapping(value = "owners/{ownerId}/pets/{petId}/rooms/{roomId}/delete")
	public String deleteRoom(@PathVariable("roomId") int roomId, @PathVariable("petId") int petId) throws DataAccessException, DuplicatedPetNameException {
		Pet pet = this.petService.findPetById(petId);
		Room room = this.roomService.findRoomById(roomId);
		pet.deleteRoom(room);
		this.petService.savePet(pet);
		
		this.roomService.deleteRoom(room);
		return "redirect:/owners/{ownerId}";
	}
	
	@GetMapping(value = "owners/{ownerId}/pets/{petId}/rooms/{roomId}/deleteB")
	public String deleteRoom2(@PathVariable("roomId") int roomId, @PathVariable("petId") int petId) throws DataAccessException, DuplicatedPetNameException {
		Pet pet = this.petService.findPetById(petId);
		Room room = this.roomService.findRoomById(roomId);
		pet.deleteRoom(room);
		this.petService.savePet(pet);
		
		this.roomService.deleteRoom(room);
		return "redirect:/owners/*/pets/{petId}/rooms/new";
	}
}
