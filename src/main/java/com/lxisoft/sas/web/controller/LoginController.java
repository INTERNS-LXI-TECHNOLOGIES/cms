package com.lxisoft.sas.web.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lxisoft.sas.domain.enumeration.Role;
import com.lxisoft.sas.service.UserDomainService;
import com.lxisoft.sas.service.dto.UserDomainDTO;
import com.lxisoft.sas.service.dto.UserRoleDTO;

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
	
	@PostMapping("do-login")
	public String loginManager(@RequestParam("username") String username, @RequestParam("password") String password) {
		UserDomainDTO user = null;
		if(this.userDomainService.findOneByRegNum(username).isPresent())			
			user = this.userDomainService.findOneByRegNum(username).get();
		if(user!=null && user.getPassword().equals(password)) {			
			this.session.setAttribute("current-user", user);
			for(UserRoleDTO role : user.getRoles()) {
				if(role.getRole() == Role.STUDENT) {
					return "redirect:/student";
				} else if(role.getRole() == Role.ADMIN) {
					return "redirect:/view-profile";
				} else if(role.getRole() == Role.FACULTY) {
					return "redirect:/faculty";
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
