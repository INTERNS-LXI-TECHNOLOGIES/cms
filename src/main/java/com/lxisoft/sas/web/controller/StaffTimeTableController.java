package com.lxisoft.sas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffTimeTableController {
	
	@GetMapping("/staff-timetable")
	public String StaffTimeTableController() {
		
		return "stafftimetable";
	}

}
