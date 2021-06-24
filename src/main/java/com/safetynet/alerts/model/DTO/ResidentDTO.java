package com.safetynet.alerts.model.DTO;


import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ResidentDTO {
	
	@Id
	@JsonIgnore
	private int id;
	
	private int stationNumber;
	
	private String address;
	
	private ArrayList<String> residents;
	
	public ResidentDTO() {
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getStationNumber() {
		return stationNumber;
	}
	
	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public ArrayList<String> getResidents() {
		return residents;
	}
	
	public void setResidents(ArrayList<String> residents) {
		this.residents = residents;
	}
	
	@Override
	public String toString() {
		return "ResidentDTO [stationNumber=" + stationNumber + 
				", address=" + address + "residents=" + residents +"]";
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ResidentDTO that = (ResidentDTO) o;
		return id == that.id;
	}

}














