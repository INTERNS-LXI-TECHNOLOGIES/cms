package com.lxisoft.sis.service.dto;

import java.io.Serializable;

public class DummyDTO implements Serializable{
	private UserDomainDTO student;
	private AddressDTO address;
	private QualificationDTO sslc;
	private QualificationDTO diploma;
	private QualificationDTO plustwo;
	public UserDomainDTO getStudent() {
		return student;
	}
	public void setStudent(UserDomainDTO student) {
		this.student = student;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public QualificationDTO getSslc() {
		return sslc;
	}
	public void setSslc(QualificationDTO sslc) {
		this.sslc = sslc;
	}
	public QualificationDTO getDiploma() {
		return diploma;
	}
	public void setDiploma(QualificationDTO diploma) {
		this.diploma = diploma;
	}
	public QualificationDTO getPlustwo() {
		return plustwo;
	}
	public void setPlustwo(QualificationDTO plustwo) {
		this.plustwo = plustwo;
	}



}
