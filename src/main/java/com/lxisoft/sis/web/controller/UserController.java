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

import com.lxisoft.sis.domain.Exam;
import com.lxisoft.sis.domain.enumeration.Role;
import com.lxisoft.sis.repository.ExamRepository;
import com.lxisoft.sis.service.dto.AddressDTO;
import com.lxisoft.sis.service.dto.DummyDTO;
import com.lxisoft.sis.service.dto.ErrorDTO;
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
import com.lxisoft.sis.web.rest.ExamHallResource;
import com.lxisoft.sis.web.rest.ExamResource;
import com.lxisoft.sis.web.rest.ExamScheduleResource;
import com.lxisoft.sis.web.rest.QualificationResource;
import com.lxisoft.sis.web.rest.SubjectResource;
import com.lxisoft.sis.web.rest.UserDomainResource;
import com.lxisoft.sis.web.rest.UserRoleResource;
import com.lxisoft.sis.web.rest.errors.ErrorConstants;

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
	SubjectResource subjectResource;
	
	
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
	ExamScheduleResource examScheduleResource;
	
@Autowired
ExamRepository examrepository;
@Autowired
HttpSession session;


	@GetMapping("/view-profile")
	public String viewProfile(Model model) {

		UserDomainDTO userDomainDTO = (UserDomainDTO) session.getAttribute("current-user");
		if(userDomainDTO == null)
		{
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

		return "admindashboard";

	}

	@GetMapping("/view-student-profile")
	public String viewStudentProfile(Model model, Pageable pageable) {

		UserDomainDTO userDomainDTO =  (UserDomainDTO) session.getAttribute("current-user");
		
		//UserDomainDTO userDomainDTO = userDomainResource.getUserDomain(Long.parseLong("3")).getBody();
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
		return "studentdashboard";
	}
	
	@GetMapping("/view-faculty-profile")
	public String viewFacultyProfile(Model model, Pageable pageable) {
		UserDomainDTO userDomainDTO =  (UserDomainDTO) session.getAttribute("current-user");

		//UserDomainDTO userDomainDTO = userDomainResource.getUserDomain(Long.parseLong("10")).getBody();
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
		return "facultydashboard";
	}

//	@PostMapping("/create-user")
//	public String createUser(@ModelAttribute DummyDTO dummy, @RequestParam("date") String date,
//			@RequestParam("month") String month, @RequestParam("year") String year,@RequestParam("role") String role, Model model)
//			throws URISyntaxException {
//		if (dummy.setValidContents()) {
//			if (dummy.getAddress() != null) {
//				AddressDTO address = addressResource.createAddress(dummy.getAddress()).getBody();
//				dummy.getUser().setAddressId(address.getId());
//			}
//			if ((date != null && month != null && year != null)
//					|| (!date.equals("") && !month.equals("") && !year.equals(""))) {
//				Instant dob = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month),
//						Integer.parseInt(date)).toInstant();
//				dummy.getUser().setDob(dob);
//				System.out.println("-----------------------------" + dob.toString());
//
//			}
//			dummy.getUser().setActivated(true);
//			dummy.getUser().setRoles(new HashSet<>());
//			if(role.equals("student")) {
//				UserRoleDTO rol=new UserRoleDTO();
//				rol.setRole(Role.STUDENT);
//				rol=this.userRoleResource.createUserRole(rol).getBody();
//				dummy.getUser().getRoles().add(rol);
//				
//				
//				
//			}
//			else if(role.equals("faculty")) {
//				UserRoleDTO rol=new UserRoleDTO();
//				rol.setRole(Role.FACULTY);
//				rol=this.userRoleResource.createUserRole(rol).getBody();
//				dummy.getUser().getRoles().add(rol);
//			}
//				 
//			dummy.setUser(userDomainResource.createUserDomain(dummy.getUser()).getBody());
//			for (QualificationDTO q : dummy.getList()) {
//				if (q != null) {
//						q.setUserDomainId(dummy.getUser().getId());
//						q = qualificationResource.createQualification(q).getBody();
//				}
//			}
//
//			return "redirect:/view-profile";
//		}
//		
//		else {
//			ErrorDTO err = new ErrorDTO("Error 500", "Can't pasre inputs",
//					"Some fields of the entered content couldn't be parsed", "UNRESOLVED");
//			model.addAttribute("error", err);
//			return "error";
//		}
//	}
	
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
				if (q.getUniversity().equals("")) {

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
	
	@PostMapping("/add-subjects")
	public String createSubjects(@ModelAttribute DummyDTO dummy, Model model) throws URISyntaxException {
		List<SubjectDTO> sub = dummy.getSubjects();
		for (SubjectDTO ss : sub) {
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

@PostMapping("/create-student-exam")
public String createStudentExam(@ModelAttribute ExamDummyDTO examdummyDTO,@RequestParam("date") String examdate,@RequestParam("month") String month, @RequestParam("year") String year,Model model ) throws URISyntaxException {

examHallResource.createExamHall(examdummyDTO.getExamhall()).getBody();
//examScheduleResource.createExamSchedule(examdummyDTO.getExamschedule()).getBody();
examResource.createExam(examdummyDTO.getExam()).getBody();

if ((examdate != null && month != null && year != null)
		|| (!examdate.equals("") && !month.equals("") && !year.equals(""))) {
	Instant ed = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month),
		Integer.parseInt(examdate)).toInstant();
	examdummyDTO.getExam().setExamDate(ed);
	System.out.println("-----------------------------" + ed.toString());

}

return viewProfile(model);
	
}
	
}





