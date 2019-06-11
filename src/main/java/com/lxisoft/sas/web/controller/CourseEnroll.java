package com.lxisoft.sas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseEnroll {

	@GetMapping("/course-enroll")
	public String CourseEnroll() {
		
		return "courseenroll";
	}

}
