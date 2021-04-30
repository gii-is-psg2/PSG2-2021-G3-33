package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {

	private final CauseRepository causeRepository;

	@Autowired
	public CauseService(final CauseRepository causeRepository) {
		this.causeRepository = causeRepository;
	}

	@Transactional
	public void saveCause(Cause cause) {
		causeRepository.save(cause);
	}

	public Cause findCauseById(int causeId) {
		return causeRepository.findByCauseId(causeId);
	}

	@Transactional
	public Collection<Cause> findCauses() {
		return causeRepository.findAll();
	}

    public String saveDonation(@Valid Cause cause, int causeId) {
		String res = "";
		Cause originalCause = this.findCauseById(causeId);
		Double totalCollected = cause.getBudgetCollected() + originalCause.getBudgetCollected();
		if (cause.getBudgetCollected() > 0 || ) {
			originalCause.setBudgetCollected(totalCollected);
			res = "redirect:/causes/";
			if (totalCollected >= originalCause.getBudgetTarget()) {
				originalCause.setIsClosed(true);
			}
			saveCause(originalCause);
		} else {
			res = "redirect:/causes/"+ causeId+"/donate";
			
		}
		return res;
    }
}
