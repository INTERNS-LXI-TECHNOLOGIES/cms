package com.lxisoft.sis.web.controller;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lxisoft.sis.service.dto.AddressDTO;
import com.lxisoft.sis.service.dto.DummyDTO;
import com.lxisoft.sis.service.dto.ErrorDTO;
import com.lxisoft.sis.service.dto.QualificationDTO;
import com.lxisoft.sis.service.dto.UserDomainDTO;
import com.lxisoft.sis.service.mapper.AddressMapper;
import com.lxisoft.sis.service.mapper.QualificationMapper;
import com.lxisoft.sis.service.mapper.UserDomainMapper;
import com.lxisoft.sis.web.rest.AddressResource;
import com.lxisoft.sis.web.rest.QualificationResource;
import com.lxisoft.sis.web.rest.UserDomainResource;
import com.lxisoft.sis.web.rest.errors.ErrorConstants;

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

		dummyDTO.setUser(new UserDomainDTO());
		model.addAttribute("dummy", dummyDTO);

		return "admindashboard";

	}

	@GetMapping("/view-student-profile")
	public String viewStudentProfile(Model model, Pageable pageable) {

		UserDomainDTO user = userDomainResource.getUserDomain(Long.parseLong("10")).getBody();
		Date d = Date.from(user.getDob());
		String date = (d.getDate() - 1) + " / " + (d.getMonth() + 1) + " / " + (d.getYear() + 1900);
		model.addAttribute("date", date);

		DummyDTO dummy = new DummyDTO();
		dummy.setUser(user);
		if (user.getAddressId() != null) {
			dummy.setAddress(addressResource.getAddress(user.getAddressId()).getBody());
		} else {
			dummy.setAddress(new AddressDTO());
		}
		dummy.setList(qualificationResource.getAllQuaficationsOfUser(pageable, user.getId()).getBody());
		model.addAttribute("dummy", dummy);
		return "studentdashboard";
	}
	
	@GetMapping("/view-faculty-profile")
	public String viewFacultyProfile(Model model, Pageable pageable) {

		UserDomainDTO user = userDomainResource.getUserDomain(Long.parseLong("3")).getBody();
		Date d = Date.from(user.getDob());
		String date = (d.getDate() - 1) + " / " + (d.getMonth() + 1) + " / " + (d.getYear() + 1900);
		model.addAttribute("date", date);

		DummyDTO dummy = new DummyDTO();
		dummy.setUser(user);
		if (user.getAddressId() != null) {
			dummy.setAddress(addressResource.getAddress(user.getAddressId()).getBody());
		} else {
			dummy.setAddress(new AddressDTO());
		}
		dummy.setList(qualificationResource.getAllQuaficationsOfUser(pageable, user.getId()).getBody());
		model.addAttribute("dummy", dummy);
		return "facultydashboard";
	}

	@PostMapping("/create-user")
	public String createUser(@ModelAttribute DummyDTO dummy, @RequestParam("date") String date,
			@RequestParam("month") String month, @RequestParam("year") String year, Model model)
			throws URISyntaxException {
		if (dummy.setValidContents()) {
			if (dummy.getAddress() != null) {
				AddressDTO address = addressResource.createAddress(dummy.getAddress()).getBody();
				dummy.getUser().setAddressId(address.getId());
			}
			if ((date != null && month != null && year != null)
					|| (!date.equals("") && !month.equals("") && !year.equals(""))) {
				Instant dob = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month),
						Integer.parseInt(date)).toInstant();
				dummy.getUser().setDob(dob);
				System.out.println("-----------------------------" + dob.toString());

			}
			dummy.getUser().setActivated(true);
			dummy.setUser(userDomainResource.createUserDomain(dummy.getUser()).getBody());
			for (QualificationDTO q : dummy.getList()) {
				if (q != null) {
						q.setUserDomainId(dummy.getUser().getId());
						q = qualificationResource.createQualification(q).getBody();
				}
			}

			return "redirect:/view-profile";
		}

		else {
			ErrorDTO err = new ErrorDTO("Error 500", "Can't pasre inputs",
					"Some fields of the entered content couldn't be parsed", "UNRESOLVED");
			model.addAttribute("error", err);
			return "error";
		}
	}
}
