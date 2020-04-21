package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DonationController {

	private final ClinicService clinicService;

	@Autowired
	public DonationController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}
	
	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("donation")
	public Donation loadCauseWithDonation(@PathVariable("causeId") final int causeId) {
		Cause cause = this.clinicService.findCauseById(causeId);
		Donation donation = new Donation();
		cause.addDonation(donation);
		return donation;
	}

	@GetMapping(value = "/causes/{causeId}/donations/new")
	public String initNewDonationForm(@PathVariable("causeId") final int causeId, final Map<String, Object> model) {
		return "donations/createOrUpdateDonationForm";
	}

	@PostMapping(value = "/causes/{causeId}/donations/new")
	public String processNewDonationForm(@Valid final Donation donation, final BindingResult result) {
		if (result.hasErrors()) {
			return "donations/createOrUpdateDonationForm";
		} else {
			donation.setDateDonation(LocalDate.now());
			this.clinicService.saveDonation(donation);
			return "redirect:/causes/{causeId}";
		}
	}
}
