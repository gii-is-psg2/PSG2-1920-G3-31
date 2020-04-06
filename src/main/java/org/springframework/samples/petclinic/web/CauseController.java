package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Causes;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CauseController {
	
	private final ClinicService clinicService;
	private final String VIEWS_CAUSE_CREATE_OR_UPDATE_FORM = "causes/createOrUpdateCauseForm";
	
	@Autowired
	public CauseController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value= {"/causes"})
	public String listCauses(final Map<String, Object> model) {
		Causes causes = new Causes();
		causes.getCauseList().addAll(this.clinicService.findCauses());
		model.put("cause", causes);
		return "causes/causeList";
	}
	
	@GetMapping("/causes/{causeId}")
	public String showCauseDetails(@PathVariable("causeId")  int causeId, Map<String,Object>  model) {
		Cause cause = this.clinicService.findCauseById(causeId);
		model.put("cause", cause);
		return "causes/causeDetails";
	}
	
	@GetMapping(value = "/causes/new")
	public String initCreationForm(final Map<String, Object> model) {
		Cause cause = new Cause();
		model.put("cause", cause);
		return VIEWS_CAUSE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/causes/new")
	public String processCreationForm(@Valid final Cause cause, final BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CAUSE_CREATE_OR_UPDATE_FORM;
		} else {
			this.clinicService.saveCause(cause);
			return "redirect:/causes/" + cause.getId();
		}
	}


}
