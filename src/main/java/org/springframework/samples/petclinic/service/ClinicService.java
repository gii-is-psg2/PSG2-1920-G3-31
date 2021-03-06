/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class ClinicService {

	private PetRepository	petRepository;

	private VetRepository	vetRepository;

	private OwnerRepository	ownerRepository;

	private VisitRepository	visitRepository;

	private BookingRepository bookingRepository;

	private CauseRepository causeRepository;

	private DonationRepository donationRepository;

	@Autowired
	public ClinicService(final PetRepository petRepository, final VetRepository vetRepository,
			final OwnerRepository ownerRepository, final VisitRepository visitRepository,
			final BookingRepository bookingRepository, final CauseRepository causeRepository, final DonationRepository donationRepository) {
		this.petRepository = petRepository;
		this.vetRepository = vetRepository;
		this.ownerRepository = ownerRepository;
		this.visitRepository = visitRepository;
		this.bookingRepository = bookingRepository;
		this.causeRepository = causeRepository;
		this.donationRepository = donationRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return this.petRepository.findPetTypes();
	}

	@Transactional
    public Specialty findSpecialtyByName(String text) {
        return this.vetRepository.findSpecialtiesByName(text);
    }

	@Transactional(readOnly = true)
	public Set<Specialty> findSpecialties(){
		return this.vetRepository.findSpecialties();
	}

	@Transactional(readOnly = true)
	public Collection<Cause> findCauses() throws DataAccessException {
		return this.causeRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Cause findCauseById(final int id) throws DataAccessException {
		return this.causeRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Owner findOwnerById(final int id) throws DataAccessException {
		return this.ownerRepository.findById(id);
	}

	public Optional<Vet> findOptionalVetById(final int vetId) {
		return this.vetRepository.findOptVetById(vetId);
	}

	@Transactional(readOnly = true)
	public Vet findVetById(final int id){
		return this.vetRepository.findVetById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(final String lastName) throws DataAccessException {
		return this.ownerRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveOwner(final Owner owner) throws DataAccessException {
		this.ownerRepository.save(owner);
	}

	@Transactional
	public void saveVisit(final Visit visit) throws DataAccessException {
		this.visitRepository.save(visit);
	}

	@Transactional(readOnly = true)
	public Pet findPetById(final int id) throws DataAccessException {
		return this.petRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Visit findVisitById(final int visitId) throws DataAccessException {
		return this.visitRepository.findById(visitId);
	}

	@Transactional
	public void savePet(final Pet pet) throws DataAccessException {
		Pet p = null;
		if(pet.getId() != null)
			 p = this.findPetById(pet.getId());
		if(p == null)
			this.petRepository.save(pet);
		else {
			p.setBirthDate(pet.getBirthDate());
			p.setName(pet.getName());
			p.setType(pet.getType());
			this.petRepository.save(p);
		}
	}

	@Transactional
	public void deletePet(final Pet pet) throws DataAccessException {
		this.petRepository.delete(pet);
	}

	@Transactional
	public void deleteOwner(final Owner owner) throws DataAccessException {
		this.ownerRepository.delete(owner);
	}

	@Transactional
	public void deleteVet(final Vet vet) throws DataAccessException {
		this.vetRepository.delete(vet);
	}

	@Transactional
	public void deleteVisit(final Visit visit) throws DataAccessException {
		this.visitRepository.delete(visit);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "vets")
	public Collection<Vet> findVets(){
		return this.vetRepository.findAll();
	}

	public Collection<Visit> findVisitsByPetId(final int petId) {
		return this.visitRepository.findByPetId(petId);
	}

	@Transactional
	public void saveVet(final Vet vet){
		this.vetRepository.save(vet);
	}

	@Transactional
	public void saveBooking(final Booking booking) throws DataAccessException {
		this.bookingRepository.save(booking);
	}

	@Transactional(readOnly = true)
	public Booking findBookingByPetId(final int petId) {
		return bookingRepository.findByPetId(petId);
	}

	@Transactional
	public void saveDonation(Donation donation) throws DataAccessException {
		this.donationRepository.save(donation);
	}

	public void saveCause(@Valid Cause cause) {
		this.causeRepository.save(cause);
	}
}
