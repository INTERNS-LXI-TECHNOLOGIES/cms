package com.lxisoft.sas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentDashboard {
	
	@GetMapping("/student")
	public String ViewStudentDashboard()
	{
		return "studentdashboard";
	}
	
	@PostMapping("/studentleave")
	public String StudentLeave()
	{
		return "redirect:/student";
	}

}
