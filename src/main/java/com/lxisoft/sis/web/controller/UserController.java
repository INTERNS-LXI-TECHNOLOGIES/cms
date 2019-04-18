package com.lxisoft.sis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.lxisoft.sis.domain.Qualification;
import com.lxisoft.sis.domain.UserDomain;
import com.lxisoft.sis.service.dto.AddressDTO;
import com.lxisoft.sis.service.dto.DummyDTO;
import com.lxisoft.sis.service.dto.QualificationDTO;
import com.lxisoft.sis.service.dto.UserDomainDTO;
import com.lxisoft.sis.web.rest.UserDomainResource;

@Controller
public class UserController {
	@Autowired
	UserDomainResource userDomainResource;
	@GetMapping("/view-profile")
	public String viewProfile(Model model) {
		
		UserDomainDTO userDomainDTO=userDomainResource.getUserDomain(Long.parseLong("1")).getBody();
		
		model.addAttribute("user", userDomainDTO);
		DummyDTO dummyDTO =new DummyDTO();
		dummyDTO.setAddress(new AddressDTO());
		dummyDTO.setDiploma(new QualificationDTO());
		dummyDTO.setPlustwo(new QualificationDTO());
		dummyDTO.setSslc(new QualificationDTO());
		dummyDTO.setStudent(new UserDomainDTO());
		model.addAttribute("dummy",dummyDTO);

	
		DummyDTO dummyDTO2 =new DummyDTO();
		dummyDTO2.setAddress(new AddressDTO());
		dummyDTO2.setDiploma(new QualificationDTO());
		dummyDTO2.setPlustwo(new QualificationDTO());
		dummyDTO2.setSslc(new QualificationDTO());
		dummyDTO2.setStudent(new UserDomainDTO());
		model.addAttribute("dummy",dummyDTO2);
	
		
		return "admindashboard";
		
	}
	@PostMapping("/create-student")
	public String createStudent(@ModelAttribute DummyDTO dummy,Model model) {
	
	
	
		return viewProfile(model);
		
	}
		
	}
	

	