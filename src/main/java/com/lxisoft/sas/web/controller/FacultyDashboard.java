package com.lxisoft.sas.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lxisoft.sas.service.dto.DummyDTO;
import com.lxisoft.sas.service.dto.UserDomainDTO;

@Controller
public class FacultyDashboard {
	@Autowired
	HttpSession session;
	@GetMapping("/faculty")
	public String ViewFacultyDashboard(Model model)
	{
		UserDomainDTO userDomainDTO = (UserDomainDTO) session.getAttribute("current-user");
		if (userDomainDTO == null) {
			return "redirect:/";
		}
		model.addAttribute("faculty", userDomainDTO);
		DummyDTO dummyDTO = new DummyDTO();
		model.addAttribute("dummy", dummyDTO);
		return "facultydashboard";
	}

}
