package com.lxisoft.sas.web.controller;

import java.net.URISyntaxException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	
	@GetMapping("/index")
	public String ViewIndex()
	{
		
		return "index";
	}
	
	@PostMapping("/login")
	public String Login(@RequestParam("username") String username,@RequestParam("password") String password,Model model) throws URISyntaxException
	{
		if(username.equals("abc") && password.equals("asd"))
		{
			
		}
		return "studentdashboard";
		
	}

}

