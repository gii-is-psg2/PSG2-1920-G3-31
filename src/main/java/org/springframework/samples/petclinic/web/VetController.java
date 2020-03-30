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

package org.springframework.samples.petclinic.web;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;


import javax.validation.Valid;
import org.springframework.samples.petclinic.model.Specialty;

import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;


/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private final ClinicService clinicService;
	
	private static final String	VIEWS_VETS_CREATE_OR_UPDATE_FORM	= "vets/createOrUpdateVetForm";


	@Autowired
	public VetController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}
	
	@ModelAttribute("specialties")
	public Collection<Specialty> populateSpecialties() {
		return this.clinicService.findSpecialties();
	}
	

	@GetMapping(value = {
		"/vets"
	})
	public String showVetList(final Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.clinicService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = {
		"/vets.xml"
	})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.clinicService.findVets());
		return vets;
	}
	
	@GetMapping(value = "/vets/new")
	public String initCreationForm(Map<String, Object> model) {
		Vet vet = new Vet();
		model.put("vet", vet);
		return VIEWS_VETS_CREATE_OR_UPDATE_FORM;
	}

	
	@PostMapping(value = "/vets/new")
	public String processCreationForm(@RequestParam(name = "specialties", required = false) Specialty specialty, @Valid final Vet vet, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("vet", vet);
			return VetController.VIEWS_VETS_CREATE_OR_UPDATE_FORM;
		} else {
			vet.addSpecialty(specialty);
			this.clinicService.saveVet(vet);
			return "redirect:/vets/";
		}
	}
	
	@GetMapping("/vets/{vetId}")
	public ModelAndView showVet(@PathVariable("vetId") int vetId) {
		ModelAndView mav = new ModelAndView("vets/vetDetails");
		mav.addObject(this.clinicService.findVetById(vetId));
		return mav;
	}
	
	@GetMapping(value = "/vets/{vetId}/edit")
	public String initUpdateVetForm(@PathVariable("vetId") int vetId, Model model) {
		Vet vet = this.clinicService.findVetById(vetId);
		model.addAttribute(vet);
		return VIEWS_VETS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vets/{vetId}/edit")
	public String processUpdateVetForm(@RequestParam(name = "specialties", required = false) Specialty specialty, @Valid Vet vet, BindingResult result,
			@PathVariable("vetId") int vetId) {
		if (result.hasErrors()) {
			return VIEWS_VETS_CREATE_OR_UPDATE_FORM;
		}
		else {
			vet.setId(vetId);
			vet.addSpecialty(specialty);
			this.clinicService.saveVet(vet);
			return "redirect:/vets/{vetId}";
		}
	}
	
	

	//Añadir el boton "delete vet"

	@GetMapping(value = "/vets/{vetId}/deleteVet")
	public String processDelete(@PathVariable("vetId") final int vetId, final Model model) {
		Vet vet = this.clinicService.findVetById(vetId);

		this.clinicService.deleteVet(vet);
		return "redirect:/vets";
	}

}
