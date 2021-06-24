package com.safetynet.alerts.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DTO.ResidentDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.service.ResidentFloodService;
import com.safetynet.alerts.util.Util;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ResidentFloodControllerTest {

	@Autowired
	private ResidentFloodController residentFloodController;

	@MockBean
	@Autowired
	private ResidentFloodService residentFloodService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private MedicalRecordService medicalRecordService;

	@Autowired
	private FirestationService firestationService;
	
	Scanner scanner;

	@Before
	private void setUp() {
		residentFloodController = new ResidentFloodController();
		residentFloodService = new ResidentFloodService();
		personService = new PersonService();
		medicalRecordService = new MedicalRecordService();
		firestationService = new FirestationService();
	}

	@Test
	public void getResidentsFloodTest() {
		ArrayList<ResidentDTO> residentFlood = new ArrayList<>();
		ArrayList<String> addresses = new ArrayList<>();
		Util util = new Util();

		scanner = new Scanner("1 2");
		ArrayList<Integer> stations = new ArrayList<Integer>();
		while (scanner.hasNextInt()) {
			stations.add(scanner.nextInt());
		}

		for (Integer station : stations) {
			ArrayList<Firestation> firestations = firestationService.getFirestations();
			for (Firestation fs : firestations) {
				if (fs.getStationNumber().equals(station)) {
					addresses.add(fs.getAddress());
				}
			}
		}
		
		for (String address : addresses) {
			
			ResidentDTO residentDTO = new ResidentDTO();
			ArrayList<String> residents = new ArrayList<>();

			ArrayList<Person> persons = personService.getPersons();
			for (Person p : persons) {
				if (p.getAddress().equals(address)) {
					String resident = new String();

					ArrayList<MedicalRecord> medicalrecords = medicalRecordService.getMedicalRecords();
					for (MedicalRecord mr : medicalrecords) {
						if (mr.getFirstName().equals(p.getFirstName())
								&& mr.getLastName().equals(p.getLastName())) {

							resident = "" + p.getFirstName() + " " + p.getLastName() + ", " + p.getPhone() + ", "
								+ util.getAge(mr.getBirthdate()) + ", " + mr.getMedications() + ", " + mr.getAllergies() + "";
						}
					}
					residents.add(resident);
				}
			}
			residentDTO.setResidents(residents);
			residentDTO.setAddress(address);

			ArrayList<Firestation> firestations = firestationService.getFirestations();
			for (Firestation fs : firestations) {
				if (fs.getAddress().equals(address)) {
					residentDTO.setStationNumber(fs.getStationNumber());
				}
			}
			residentFlood.add(residentDTO);
		}
		
		when(residentFloodService.getResidentFlood("1 2")).thenReturn(residentFlood);

		ArrayList<ResidentDTO> getResidentFlood = residentFloodController.getResidentFlood("1 2");
		
		assertThat(getResidentFlood).isEqualTo(residentFlood);
	}

	@Test
	public void getNoResidentFloodTest() {

		when(residentFloodService.getResidentFlood("1 2")).thenReturn(null);

		ArrayList<ResidentDTO> getResidentFlood = residentFloodController.getResidentFlood("1 2");

		assertThat(getResidentFlood).isEqualTo(null);
	}



}
