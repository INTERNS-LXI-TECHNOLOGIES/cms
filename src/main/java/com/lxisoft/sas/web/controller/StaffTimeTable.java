package com.lxisoft.sas.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lxisoft.sas.domain.enumeration.Department;
import com.lxisoft.sas.domain.enumeration.Role;
import com.lxisoft.sas.service.UserDomainService;
import com.lxisoft.sas.service.dto.UserDomainDTO;

@Controller
@RequestMapping("/staffTimeTable")
public class StaffTimeTable {
	
	@Autowired
	UserDomainService userDomainService;
	
	@GetMapping()
	public String redirectToPage(@RequestParam("dept") Department dept, Model model) {
		List<UserDomainDTO> users = userDomainService.findAllWithEagerRelationships(null).getContent();
		users = users.stream().filter(user-> 
			user.getDepartment().equals(dept)).collect(Collectors.toList());
		List<UserDomainDTO> faculties = new ArrayList<>();
		users.forEach(usr->{
			usr.getRoles().forEach(rol -> {
				if(rol.getRole().equals(Role.FACULTY))
					faculties.add(usr);
			});
		});
		model.addAttribute("staffs",faculties);
		return "stafftimetable";
	}
}
