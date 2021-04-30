package org.springframework.samples.petclinic.repository;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.AdoptionApplications;

public interface AdoptionApplicationRepository extends Repository<AdoptionApplications, Integer> {

	void save(AdoptionApplications application) throws DataAccessException;
	
	void delete(AdoptionApplications application) throws DataAccessException;
	
	AdoptionApplications findById(int id) throws DataAccessException;
	
	@Query("SELECT p.applications FROM Pet p where p.id=:petId")
	Collection<AdoptionApplications> findByPetId(@Param(value = "petId") int petId);
}
