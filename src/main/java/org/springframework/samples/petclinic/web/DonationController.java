package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DonationController {

	private final ClinicService clinicService;

	private final String VIEWS_DONATION_CREATE_FORM = "donations/createForm";

	@Autowired
	public DonationController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@GetMapping(value = "donations/createForm")
	public String initDonationForm(final Map<String, Object> model) {
		Donation donation = new Donation();
		model.put("donation", donation);
		return this.VIEWS_DONATION_CREATE_FORM;
	}

	@GetMapping(value = "/causes/{causeId}/donations/createForm")
	public String processDonationForm(@Valid Donation donation, BindingResult result,
			@RequestParam(name = "causeId") int causeId) {
		if (result.hasErrors()) {
			return this.VIEWS_DONATION_CREATE_FORM;
		} else {
			this.clinicService.saveDonation(donation);
			return "redirect:/causes/" + causeId;
		}
	}
}
