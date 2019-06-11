package com.lxisoft.sis.web.controller;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lxisoft.sis.domain.enumeration.Department;
import com.lxisoft.sis.domain.enumeration.Role;
import com.lxisoft.sis.domain.enumeration.Semester;
import com.lxisoft.sis.repository.ExamRepository;
import com.lxisoft.sis.service.dto.AddressDTO;
import com.lxisoft.sis.service.dto.DummyDTO;
import com.lxisoft.sis.service.dto.EventDTO;
import com.lxisoft.sis.service.dto.ExamDTO;
import com.lxisoft.sis.service.dto.ExamDummyDTO;
import com.lxisoft.sis.service.dto.ExamHallDTO;
import com.lxisoft.sis.service.dto.ExamScheduleDTO;
import com.lxisoft.sis.service.dto.QualificationDTO;
import com.lxisoft.sis.service.dto.SubjectDTO;
import com.lxisoft.sis.service.dto.UserDomainDTO;
import com.lxisoft.sis.service.dto.UserRoleDTO;
import com.lxisoft.sis.service.mapper.AddressMapper;
import com.lxisoft.sis.service.mapper.QualificationMapper;
import com.lxisoft.sis.service.mapper.UserDomainMapper;
import com.lxisoft.sis.web.rest.AddressResource;
import com.lxisoft.sis.web.rest.EventResource;
import com.lxisoft.sis.web.rest.ExamHallResource;
import com.lxisoft.sis.web.rest.ExamResource;
import com.lxisoft.sis.web.rest.ExamScheduleResource;
import com.lxisoft.sis.web.rest.QualificationResource;
import com.lxisoft.sis.web.rest.SubjectResource;
import com.lxisoft.sis.web.rest.UserDomainResource;
import com.lxisoft.sis.web.rest.UserRoleResource;

@Controller
public class UserController {
	@Autowired
	UserDomainResource userDomainResource;
	@Autowired
	ExamHallResource examHallResource;
	@Autowired
	ExamResource examResource;
	@Autowired
	UserRoleResource userRoleResource;
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
	@Autowired
	HttpSession session;
	@Autowired
	ExamRepository examrepository;
	@Autowired
	ExamScheduleResource examScheduleResource;
	@Autowired
	EventResource eventResource;
	@Autowired
	SubjectResource subjectResource;

	@GetMapping("/view-profile")
	public String viewProfile(Model model) {
		UserDomainDTO userDomainDTO = (UserDomainDTO) session.getAttribute("current-user");
		if (userDomainDTO == null) {
			return "redirect:/";
		}
		model.addAttribute("admin", userDomainDTO);
		DummyDTO dummyDTO = new DummyDTO();
		dummyDTO.setAddress(new AddressDTO());
		dummyDTO.setUser(new UserDomainDTO());
		model.addAttribute("dummy", dummyDTO);
		ExamDummyDTO examdummyDTO = new ExamDummyDTO();
		examdummyDTO.setExam(new ExamDTO());
		examdummyDTO.setExamhall(new ExamHallDTO());
		examdummyDTO.setExamschedule(new ExamScheduleDTO());
		model.addAttribute("examdummy", examdummyDTO);
		model.addAttribute("event", new EventDTO());
		model.addAttribute("allEvents", eventResource.getAllEvents(null, true).getBody());
		return "admindashboard";
	}

	@GetMapping("/view-student-profile")
	public String viewStudentProfile(Model model, Pageable pageable) {
		
		UserDomainDTO userDomainDTO = (UserDomainDTO) session.getAttribute("current-user");
		if (userDomainDTO == null) {
			return "redirect:/";
		}

		//UserDomainDTO user = userDomainResource.getUserDomain(Long.parseLong("10")).getBody();
		Date d = Date.from(userDomainDTO.getDob());
		String date = (d.getDate() - 1) + " / " + (d.getMonth() + 1) + " / " + (d.getYear() + 1900);
		model.addAttribute("date", date);

		DummyDTO dummy = new DummyDTO();
		dummy.setUser(userDomainDTO);
		if (userDomainDTO.getAddressId() != null) {
			dummy.setAddress(addressResource.getAddress(userDomainDTO.getAddressId()).getBody());
		} else {
			dummy.setAddress(new AddressDTO());
		}
		dummy.setList(qualificationResource.getAllQuaficationsOfUser(pageable, userDomainDTO.getId()).getBody());
		model.addAttribute("dummy", dummy);
		model.addAttribute("event", new EventDTO());
		model.addAttribute("allEvents", eventResource.getAllEvents(null, true).getBody());
		return "studentdashboard";
	}

	@GetMapping("/view-faculty-profile")
	public String viewFacultyProfile(Model model, Pageable pageable) {
		
		UserDomainDTO userDomainDTO = (UserDomainDTO) session.getAttribute("current-user");
		if (userDomainDTO == null) {
			return "redirect:/";
		}

		//UserDomainDTO user = userDomainResource.getUserDomain(Long.parseLong("3")).getBody();
		Date d = Date.from(userDomainDTO.getDob());
		String date = (d.getDate() - 1) + " / " + (d.getMonth() + 1) + " / " + (d.getYear() + 1900);
		model.addAttribute("date", date);

		DummyDTO dummy = new DummyDTO();
		dummy.setUser(userDomainDTO);
		if (userDomainDTO.getAddressId() != null) {
			dummy.setAddress(addressResource.getAddress(userDomainDTO.getAddressId()).getBody());
		} else {
			dummy.setAddress(new AddressDTO());
		}
		dummy.setList(qualificationResource.getAllQuaficationsOfUser(pageable, userDomainDTO.getId()).getBody());
		model.addAttribute("dummy", dummy);
		model.addAttribute("event", new EventDTO());
		model.addAttribute("allEvents", eventResource.getAllEvents(null, true).getBody());
		return "facultydashboard";
	}

	@PostMapping("/create-user")
	public String createUser(@ModelAttribute DummyDTO dummy, @RequestParam("date") String date,
			@RequestParam("month") String month, @RequestParam("year") String year, @RequestParam("role") String role,
			Model model) throws URISyntaxException {
		dummy.setValidContents();
		saveAddressAndDOB(dummy, date, month, year);
		dummy.getUser().setActivated(true);
		setRoles(dummy, role);
		dummy.setUser(userDomainResource.createUserDomain(dummy.getUser()).getBody());
		saveQualifications(dummy);
		return "redirect:/view-profile";
	}

	public void saveQualifications(DummyDTO dummy) throws URISyntaxException {
		for (QualificationDTO q : dummy.getList()) {
			if (q != null) {
				if (q.getUniversity() == null || q.getUniversity().equals("")) {

				} else {
					q.setUserDomainId(dummy.getUser().getId());
					q = qualificationResource.createQualification(q).getBody();
				}

			}
		}
	}

	public void saveAddressAndDOB(DummyDTO dummy, String date, String month, String year) throws URISyntaxException {
		if (dummy.getAddress() != null) {
			AddressDTO address = addressResource.createAddress(dummy.getAddress()).getBody();
			dummy.getUser().setAddressId(address.getId());
		}
		if ((date != null && month != null && year != null)
				&& (!date.equals("") && !month.equals("") && !year.equals(""))) {
			Instant dob = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date))
					.toInstant();
			dummy.getUser().setDob(dob);
		}
	}

	public void setRoles(DummyDTO dummy, String role) throws URISyntaxException {
		dummy.getUser().setRoles(new HashSet<>());
		if (role.equals("student")) {
			UserRoleDTO rol = new UserRoleDTO();
			rol.setRole(Role.STUDENT);
			rol = this.userRoleResource.createUserRole(rol).getBody();
			dummy.getUser().getRoles().add(rol);
		}

		else if (role.equals("faculty")) {
			UserRoleDTO rol = new UserRoleDTO();
			rol.setRole(Role.FACULTY);
			rol = this.userRoleResource.createUserRole(rol).getBody();
			dummy.getUser().getRoles().add(rol);
		}
	}

//@PostMapping("/create-student-exam")
//public String createStudentExam(@ModelAttribute ExamDummyDTO examdummyDTO,Model model ) throws URISyntaxException {
//	examHallResource.createExamHall(examdummyDTO.getExamhall());
//	examScheduleResource.createExamSchedule(examdummyDTO.getExamschedule());
//	examResource.createExam(examdummyDTO.getExam());
//	
//	return viewProfile(model);
//	
//	
//	
//	
//
//	
//}
	
	@PostMapping("/add-subjects")
	public String createSubjects(@ModelAttribute DummyDTO dummy, @RequestParam("subdept") Department subdept,
			@RequestParam("subsem") Semester subsem, Model model) throws URISyntaxException {
		List<SubjectDTO> sub = dummy.getSubjects();
		for (SubjectDTO ss : sub) {
			
			ss.setDepartment(subdept);
			ss.setSemester(subsem);
			
			if (ss != null) {
				if (ss.getSubjectCode().equals("")) {

				} else {
					ss.setSubjectCode(ss.getSubjectCode());

					ss = subjectResource.createSubject(ss).getBody();
				}

			}
		}	

		return "redirect:/view-profile";
	}
	
	


	@PostMapping("/create-an-event")
	public String createEvent(@ModelAttribute EventDTO event, @RequestParam("event-date") String date)
			throws URISyntaxException {
		String[] dates = date.split("-");
		if ((dates[0] != null && dates[1] != null && dates[2] != null)
				|| (!dates[0].equals("") && !dates[1].equals("") && !dates[2].equals(""))) {
			Instant eventDate = new GregorianCalendar(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]),
					Integer.parseInt(dates[0])).toInstant();
			event.setEventDate(eventDate);
		}
		event.setActive(true);
		eventResource.createEvent(event);
		return "redirect:/view-profile";
	}

}