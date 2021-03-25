package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {

	private RoomRepository roomRepository;
	
	@Autowired
	public RoomService( RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}
	
	@Transactional
	public void saveRoom(Room room) {
		this.roomRepository.save(room);
	}
	
	@Transactional
	public void deleteRoom(Room room) throws DataAccessException {
		this.roomRepository.delete(room);
	}
	
	@Transactional(readOnly = true)
	public Collection<Room> findRoomsByPetId(int petId){
		return this.roomRepository.findRoomsByPetId(petId);
	}
	
	@Transactional(readOnly = true)
	public Room findRoomById(int roomId) {
		return this.roomRepository.findById(roomId);
	}
}
