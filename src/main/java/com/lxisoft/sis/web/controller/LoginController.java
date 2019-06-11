package com.lxisoft.sis.web.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lxisoft.sis.domain.enumeration.Role;
import com.lxisoft.sis.service.UserDomainService;
import com.lxisoft.sis.service.dto.UserDomainDTO;
import com.lxisoft.sis.service.dto.UserRoleDTO;

@Controller
@RequestMapping()
public class LoginController {
	
	@Autowired
	private UserDomainService userDomainService;
	@Autowired
	private HttpSession session;
	

	@GetMapping()
	public String redirectLogin() {
		return "index";
	}
	
	@PostMapping("/do-login")
	public String loginManager(@RequestParam("username") String username, @RequestParam("password") String password) {
		UserDomainDTO user = this.userDomainService.findOneByRegNum(username).get();
		if(user!=null && user.getPassword().equals(password)) {			
			this.session.setAttribute("current-user", user);
			for(UserRoleDTO role : user.getRoles()) {
				if(role.getRole() == Role.STUDENT) {
					return "redirect:/view-student-profile";
				} else if(role.getRole() == Role.ADMIN) {
					return "redirect:/view-profile";
				} else if(role.getRole() == Role.FACULTY) {
					return "redirect:/view-faculty-profile";
				} else {
					session.removeAttribute("current-user");
				}
			}
			return redirectLogin();
		}
		else {
			return redirectLogin();
		}
	}
	
	@GetMapping("/user-logout")
	public String logout() {
		session.removeAttribute("current-user");
		return redirectLogin();
	}
}
