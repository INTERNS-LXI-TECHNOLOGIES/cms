package com.lxisoft.sas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FacultyDashboard {

	@GetMapping("/faculty")
	public String ViewFacultyDashboard()
	{
		return "facultydashboard";
	}

}
