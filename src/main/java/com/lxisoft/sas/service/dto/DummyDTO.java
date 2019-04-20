package com.lxisoft.sas.service.dto;
import java.util.ArrayList;

public class DummyDTO {
	private UserDomainDTO user;
	private AddressDTO address;
	ArrayList<QualificationDTO> list=new ArrayList<QualificationDTO>();
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
}
