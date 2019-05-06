package com.lxisoft.sas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentDashboard {
	
	@GetMapping("/student")
	public String ViewStudentDashboard()
	{
		return "studentdashboard";
	}

}
