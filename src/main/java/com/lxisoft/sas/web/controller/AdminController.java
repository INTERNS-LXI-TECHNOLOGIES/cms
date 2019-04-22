package com.lxisoft.sas.web.controller;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.lxisoft.sas.service.dto.AddressDTO;
import com.lxisoft.sas.service.dto.DummyDTO;
import com.lxisoft.sas.service.dto.QualificationDTO;
import com.lxisoft.sas.service.dto.UserDomainDTO;
import com.lxisoft.sas.service.mapper.AddressMapper;
import com.lxisoft.sas.service.mapper.QualificationMapper;
import com.lxisoft.sas.service.mapper.UserDomainMapper;
import com.lxisoft.sas.web.rest.AddressResource;
import com.lxisoft.sas.web.rest.QualificationResource;
import com.lxisoft.sas.web.rest.UserDomainResource;



@Controller
	public class AdminController
	{
		@Autowired
		UserDomainResource userDomainResource;
		@Autowired
		AddressResource addressResource;
		@Autowired
		QualificationResource qualificationResource;
		@Autowired
		UserDomainMapper userDomainMapper;
		@Autowired
		QualificationMapper qualificationMapper;
		@Autowired
		AddressMapper addressMapper;
		
		@GetMapping("/view-profile")
		public String viewProfile(Model model) 
		{
			
			UserDomainDTO userDomainDTO = userDomainResource.getUserDomain(Long.parseLong("16")).getBody();
			model.addAttribute("admin", userDomainDTO);
			DummyDTO dummyDTO = new DummyDTO();
			dummyDTO.setAddress(new AddressDTO());
			dummyDTO.getList().add(new QualificationDTO());

			dummyDTO.setUser(new UserDomainDTO());
			model.addAttribute("dummy", dummyDTO);
	         return "admindashboard";
		}
		
		@PostMapping("/create-user")
		public String createUser(@ModelAttribute DummyDTO dummy, Model model) throws URISyntaxException {
			AddressDTO address = addressResource.createAddress(dummy.getAddress()).getBody();
		    dummy.getUser().setAddressId(address.getId());
			dummy.setUser(userDomainResource.createUserDomain(dummy.getUser()).getBody());
			
			for (QualificationDTO q : dummy.getList()) {
				if(q!=null)
				{
					
					if(q.getUniversity().equals(""))
					{
						dummy.getList().remove(q);	
					
					}
					else
					{
						q.setUserDomainId(dummy.getUser().getId());
						q = qualificationResource.createQualification(q).getBody();
					}
				
				}
			}
	       
	        
			return "redirect:/view-profile	";
			}
	}

