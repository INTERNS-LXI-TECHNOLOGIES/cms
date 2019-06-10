package com.lxisoft.sis.service.dto;

public class ExamDummyDTO {
	private ExamDTO exam;
	private ExamHallDTO examhall;
	private ExamScheduleDTO examschedule;
	public ExamDTO getExam() {
		return exam;
	}
	public void setExam(ExamDTO exam) {
		this.exam = exam;
	}
	public ExamHallDTO getExamhall() {
		return examhall;
	}
	public void setExamhall(ExamHallDTO examhall) {
		this.examhall = examhall;
	}
	public ExamScheduleDTO getExamschedule() {
		return examschedule;
	}
	public void setExamschedule(ExamScheduleDTO examschedule) {
		this.examschedule = examschedule;
	}
	

}
