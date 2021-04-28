package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.AdoptionApplications;
import org.springframework.samples.petclinic.repository.AdoptionApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionApplicationService {

	private AdoptionApplicationRepository adoptionApplicationRepository;
	
	@Autowired
	public AdoptionApplicationService (AdoptionApplicationRepository adoptionApplicationRepository) {
		this.adoptionApplicationRepository = adoptionApplicationRepository;
	}
	
	@Transactional
	public void saveAdoptionApplication(AdoptionApplications application) {
		this.adoptionApplicationRepository.save(application);
	}
	
	@Transactional
	public void deleteAdoptionApplication(AdoptionApplications application) {
		this.adoptionApplicationRepository.delete(application);
	}
	
	@Transactional(readOnly = true)
	public Collection<AdoptionApplications> findAdoptionApplicationsByPetId(int petId){
		return adoptionApplicationRepository.findByPetId(petId);
	}
	
	@Transactional(readOnly = true)
	public AdoptionApplications findApplicationById(int id) throws DataAccessException {
		return this.adoptionApplicationRepository.findById(id);
	}
}
