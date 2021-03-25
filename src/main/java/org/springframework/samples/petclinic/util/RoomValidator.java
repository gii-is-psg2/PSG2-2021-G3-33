package org.springframework.samples.petclinic.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.samples.petclinic.model.Room;

public class RoomValidator {

	private RoomValidator() {
		new RoomValidator();
	}
	
	public static boolean validateDates(Room room) {
		
		boolean date = false;
		if(room.getStartDate().isAfter(room.getEndDate())) {
			date = true;
		}
		return date;
	}
	
	public static boolean validatesDatesToday(Room room) {
		boolean date = false;
		if(room.getStartDate().isBefore(LocalDate.now())) {
			date = true;
		}
		return date;
	}
	
	public static boolean validateRoomsOccupied(Room room, Collection<Room> rooms) {
		boolean ocupado = false;
		List<Room> roomsOfAPet = new ArrayList<>();
		
		roomsOfAPet.addAll(rooms);
		
		LocalDate newStartDate = room.getStartDate();
		LocalDate newEndDate = room.getEndDate();
		
		for(int i = 0; i < rooms.size();i++) {
			LocalDate actualStartDate = roomsOfAPet.get(i).getStartDate();
			LocalDate actualEndDate = roomsOfAPet.get(i).getEndDate();

			if((newStartDate.isBefore(actualEndDate) && newEndDate.isAfter(actualEndDate))
					|| (newStartDate.isAfter(actualStartDate) && newStartDate.isBefore(actualEndDate))
					|| (newStartDate.isAfter(actualStartDate) && newStartDate.isBefore(actualEndDate))
					|| (newEndDate.isAfter(actualStartDate) && newEndDate.isBefore(actualEndDate))
					|| (newStartDate.isBefore(actualStartDate) && newEndDate.isEqual(actualEndDate))
					|| (newStartDate.isEqual(actualStartDate) && newEndDate.isEqual(actualEndDate))){
				
				ocupado = true;
			}
		}
		return ocupado;
	}
}
