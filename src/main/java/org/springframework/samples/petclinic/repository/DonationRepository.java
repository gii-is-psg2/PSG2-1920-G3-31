package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Donation;

public interface DonationRepository {
	
	void save(Donation donation) throws DataAccessException;
	
}
