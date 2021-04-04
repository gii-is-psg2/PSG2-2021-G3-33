package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Room;

public interface RoomRepository extends Repository<Room, Integer> {

	void save(Room room);
	
	Room findById(int roomId) throws DataAccessException;
	
	void delete(Room room) throws DataAccessException;
	
	@Query("SELECT p.rooms FROM Pet p where p.id=:petId")
	Collection<Room> findRoomsByPetId(@Param(value = "petId") int petId);
	
	
}
