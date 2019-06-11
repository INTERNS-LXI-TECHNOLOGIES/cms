package com.lxisoft.sas.web.controller;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lxisoft.sas.service.dto.LeaveApplicationDTO;
import com.lxisoft.sas.service.dto.UserDomainDTO;
import com.lxisoft.sas.web.rest.LeaveApplicationResource;

@Controller
@RequestMapping("/student")
public class StudentDashboard {
	@Autowired
	HttpSession session;
	@Autowired
	LeaveApplicationResource leaveApplicationResource;

	@GetMapping()
	public String ViewStudentDashboard(Model model) {
		UserDomainDTO userDomainDTO = (UserDomainDTO) session.getAttribute("current-user");
		if (userDomainDTO == null) {
			return "redirect:/";
		}
		model.addAttribute("leave", new LeaveApplicationDTO());
		model.addAttribute("student", userDomainDTO);
		return "studentdashboard";
	}

	@PostMapping("/apply-leave")
	public String applyLeave(@ModelAttribute LeaveApplicationDTO leave, @RequestParam("dateFrom") String fromDate,
			@RequestParam("dateTo") String toDate) throws URISyntaxException {
		String[] fromDates = fromDate.split("-");
		if ((fromDates[0] != null && fromDates[1] != null && fromDates[2] != null)
				|| (!fromDates[0].equals("") && !fromDates[1].equals("") && !fromDates[2].equals(""))) {
			Instant from = new GregorianCalendar(Integer.parseInt(fromDates[2]), Integer.parseInt(fromDates[1]),
					Integer.parseInt(fromDates[0])).toInstant();
			leave.setFromDate(from);
		}
		String[] toDates = fromDate.split("-");
		if ((toDates[0] != null && toDates[1] != null && toDates[2] != null)
				|| (!toDates[0].equals("") && !toDates[1].equals("") && !toDates[2].equals(""))) {
			Instant to = new GregorianCalendar(Integer.parseInt(toDates[2]), Integer.parseInt(toDates[1]),
					Integer.parseInt(toDates[0])).toInstant();
			leave.setToDate(to);
		}
		leave.setAppliedById(((UserDomainDTO) session.getAttribute("current-user")).getId());
		this.leaveApplicationResource.createLeaveApplication(leave);
		return "redirect:/student";
	}

}
