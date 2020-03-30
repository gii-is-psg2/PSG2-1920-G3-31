package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyFormatter implements Formatter<Specialty>{

	private ClinicService clinicService;

    @Autowired
    public SpecialtyFormatter(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @Override
    public Specialty parse(String text, Locale locale) throws ParseException {
        Specialty spec = this.clinicService.findSpecialtyByName(text);
        if(spec == null) {
            throw new ParseException("The text doesn't match with any Specialty: " + text, 0);
        }
        return spec;
    }

    @Override
    public String print(Specialty specialty, Locale locale) {
        return specialty.getName();
    }

}
