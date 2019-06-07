package com.lxisoft.sas.web.controller;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lxisoft.sas.service.dto.AddressDTO;
import com.lxisoft.sas.service.dto.DummyDTO;
import com.lxisoft.sas.service.dto.ErrorDTO;
import com.lxisoft.sas.service.dto.QualificationDTO;
import com.lxisoft.sas.service.dto.SubjectDTO;
import com.lxisoft.sas.service.dto.UserDomainDTO;
import com.lxisoft.sas.service.mapper.AddressMapper;
import com.lxisoft.sas.service.mapper.QualificationMapper;
import com.lxisoft.sas.service.mapper.UserDomainMapper;
import com.lxisoft.sas.web.rest.AddressResource;
import com.lxisoft.sas.web.rest.QualificationResource;
import com.lxisoft.sas.web.rest.SubjectResource;
import com.lxisoft.sas.web.rest.UserDomainResource;



@Controller
	public class AdminController
	{
	
		@Autowired
		SubjectResource subjectResource;
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
		public String viewProfile(Model model) 
		{
			
			UserDomainDTO userDomainDTO = userDomainResource.getUserDomain(Long.parseLong("1")).getBody();
			model.addAttribute("admin", userDomainDTO);
			
			DummyDTO dummyDTO = new DummyDTO();
			dummyDTO.setAddress(new AddressDTO());
			dummyDTO.getList().add(new QualificationDTO());
			dummyDTO.getSubList().add(new SubjectDTO());
			dummyDTO.setUser(new UserDomainDTO());
			model.addAttribute("dummy", dummyDTO);
	         return "admindashboard";
		}
		
		@PostMapping("/create-user")
		public String createUser(@ModelAttribute DummyDTO dummy,@RequestParam("date") String date,
				@RequestParam("month") String month, @RequestParam("year") String year, Model model) throws URISyntaxException {
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
				if(q!=null)
				{	
					if(q.getUniversity().equals(""))
					{
					
					
					}
					else
					{
						q.setUserDomainId(dummy.getUser().getId());
						q = qualificationResource.createQualification(q).getBody();
					}
				
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
		
		
		@PostMapping("/class-timetable")
		public String createClassTimetable(@ModelAttribute DummyDTO dummy,Model model) throws URISyntaxException {
			List <SubjectDTO>a=dummy.getSubList();
			for (SubjectDTO s : a) {
				if(s!=null)
				{	
					if(s.getSubjectCode().equals(""))
					{

					
					}
					else
					{
						s.setSubjectCode(s.getSubjectCode());
						
						s = subjectResource.createSubject(s).getBody();
					}
				
				}
		}
				
			return "redirect:/view-profile";			
		}
		
		@PostMapping("/staff-timetable")
		public String createStaffTimetable(@ModelAttribute DummyDTO dummy,Model model) throws URISyntaxException {
			List <SubjectDTO>staff=dummy.getSubList();
			for (SubjectDTO stt : staff) {
				if(stt!=null)
				{	
					if(stt.getSubjectCode().equals(""))
					{
				
					}
					else
					{
						stt.setSubjectCode(stt.getSubjectCode());
						stt = subjectResource.createSubject(stt).getBody();
					}
				
				}
		}
				
			return "redirect:/view-profile";			
		}
		
}

