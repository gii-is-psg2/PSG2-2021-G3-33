package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository extends Repository<Cause, Integer> {

	void save(Cause cause);

	@Query("SELECT c FROM Cause c where c.id=:causeId")
	Cause findByCauseId(@Param(value = "causeId") int causeId);

	@Query("SELECT c FROM Cause c")
	Collection<Cause> findAll();

}
