package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.BookingRepository;

public interface SpringDataBookingRepository extends BookingRepository, Repository<Booking, Integer> {

}
