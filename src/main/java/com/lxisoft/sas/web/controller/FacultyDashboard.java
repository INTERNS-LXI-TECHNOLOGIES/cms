package com.lxisoft.sas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lxisoft.sas.service.dto.DummyDTO;

@Controller
public class FacultyDashboard {

	@GetMapping("/faculty")
	public String ViewFacultyDashboard(Model model)
	{
		DummyDTO dummyDTO = new DummyDTO();
		model.addAttribute("dummy", dummyDTO);
		return "facultydashboard";
	}

}
