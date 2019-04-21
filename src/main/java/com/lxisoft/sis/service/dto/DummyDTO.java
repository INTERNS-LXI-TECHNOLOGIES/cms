package com.lxisoft.sis.service.dto;

import java.util.ArrayList;

public class DummyDTO {
	private UserDomainDTO user;
	private AddressDTO address;
	ArrayList<QualificationDTO> list = new ArrayList<QualificationDTO>();

	public UserDomainDTO getUser() {
		return user;
	}

	public void setUser(UserDomainDTO user) {
		this.user = user;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public ArrayList<QualificationDTO> getList() {
		return list;
	}

	public void setList(ArrayList<QualificationDTO> list) {
		this.list = list;
	}

	public boolean setValidContents() {
		if (user.getEmail() == null || user.getEmail().equals("")) {
			return false;
		} else if (user.getRegNum() == null || user.getRegNum().equals("")) {
			return false;
		} else if (user.getPassword() == null || user.getPassword() == "") {
			return false;
		}
		return validateAddress();
	}

	private boolean validateAddress() {
		if (address.getCountry().equals("")) {
			address.setCountry(null);
		}
		if (address.getDistrict().equals("")) {
			address.setDistrict(null);
		}
		if (address.getPincode().equals(0)) {
			address.setPincode(null);
		}
		if (address.getHouseName().equals("")) {
			address.setHouseName(null);
		}
		if (address.getState().equals("")) {
			address.setState(null);
		}
		if (address.getStreet().equals("")) {
			address.setStreet(null);
		}
		if (address.getCountry() == null && address.getDistrict() == null && address.getHouseName() == null
				&& address.getPincode() == null && address.getState() == null && address.getStreet() == null) {
			address = null;
		}
		return validateQualifications();
	}

	private boolean validateQualifications() {
		for (QualificationDTO q : list) {
			if (q.getGrade().equals("")) {
				q.setGrade(null);
			}
			if (q.getUniversity().equals("")) {
				q.setUniversity(null);
			}
			if (q.getGrade() == null && q.getMarks() == null && q.getPercentage() == null && q.getUniversity() == null
					&& q.getYear() == null) {
				q = null;
			}
		}
		return true;
	}

}
