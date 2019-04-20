package com.lxisoft.sas.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lxisoft.sas.service.dto.UserDomainDTO;
import com.lxisoft.sas.web.rest.UserDomainResource;



@Controller
	public class AdminController
	{
		@Autowired
		UserDomainResource userDomainResource;
		
		@GetMapping("/view-profile")
		public String viewProfile(Model model) 
		{
			
			UserDomainDTO userDomainDTO = userDomainResource.getUserDomain(Long.parseLong("1")).getBody();
			model.addAttribute("admin", userDomainDTO);
	         return "admindashboard";
		}
	}

