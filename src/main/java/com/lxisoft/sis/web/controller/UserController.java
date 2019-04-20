package com.lxisoft.sis.web.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.lxisoft.sis.domain.Address;
import com.lxisoft.sis.domain.Qualification;
import com.lxisoft.sis.domain.UserDomain;
import com.lxisoft.sis.service.dto.AddressDTO;
import com.lxisoft.sis.service.dto.DummyDTO;
import com.lxisoft.sis.service.dto.QualificationDTO;
import com.lxisoft.sis.service.dto.UserDomainDTO;
import com.lxisoft.sis.service.mapper.AddressMapper;
import com.lxisoft.sis.service.mapper.QualificationMapper;
import com.lxisoft.sis.service.mapper.UserDomainMapper;
import com.lxisoft.sis.web.rest.AddressResource;
import com.lxisoft.sis.web.rest.QualificationResource;
import com.lxisoft.sis.web.rest.UserDomainResource;

@Controller
public class UserController {
	@Autowired
	UserDomainResource userDomainResource;
	@Autowired
	AddressResource addressResource;
	@Autowired
	QualificationResource qualificationResource;
	@Autowired
	UserDomainMapper userDomainMapper;
	@Autowired
	QualificationMapper qualificationMapper;
	@Autowired
	AddressMapper addressMapper;

	@GetMapping("/view-profile")
	public String viewProfile(Model model) {

		UserDomainDTO userDomainDTO = userDomainResource.getUserDomain(Long.parseLong("1")).getBody();

		model.addAttribute("admin", userDomainDTO);

		DummyDTO dummyDTO = new DummyDTO();
		dummyDTO.setAddress(new AddressDTO());
		dummyDTO.getList().add(new QualificationDTO());

		dummyDTO.setUser(new UserDomainDTO());
		model.addAttribute("dummy", dummyDTO);

		return "admindashboard";

	}

	@PostMapping("/create-user")
	public String createUser(@ModelAttribute DummyDTO dummy, Model model) throws URISyntaxException {
		AddressDTO address = addressResource.createAddress(dummy.getAddress()).getBody();
		dummy.getUser().setAddressId(address.getId());
		dummy.setUser(userDomainResource.createUserDomain(dummy.getUser()).getBody());

		for (QualificationDTO q : dummy.getList()) {
			if (q != null)

				q.setUserDomainId(dummy.getUser().getId());
			q = qualificationResource.createQualification(q).getBody();
		}

		System.out.println(userDomainResource.getUserDomain(dummy.getUser().getId()).getBody());

		return "redirect:/view-profile";
	}

	@GetMapping("/view-student-profile")
	public String viewStudentProfile(Model model, Pageable pageable) {

		UserDomainDTO user = userDomainResource.getUserDomain(Long.parseLong("2")).getBody();
//		model.addAttribute("student", user);

		DummyDTO dummy = new DummyDTO();
		dummy.setUser(user);
		dummy.setAddress(addressResource.getAddress(user.getAddressId()).getBody());
		dummy.setList(qualificationResource.getAllQuaficationsOfUser(pageable, user.getAddressId()).getBody());
		model.addAttribute("dummy", dummy);
		return "studentdashboard";

	}
}
