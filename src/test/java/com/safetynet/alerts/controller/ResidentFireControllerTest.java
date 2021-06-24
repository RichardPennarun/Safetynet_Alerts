package com.safetynet.alerts.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

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
import com.safetynet.alerts.service.ResidentFireService;
import com.safetynet.alerts.util.Util;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ResidentFireControllerTest {

	@Autowired
	private ResidentFireController residentFireController;

	@MockBean
	@Autowired
	private ResidentFireService residentFireService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private MedicalRecordService medicalRecordService;

	@Autowired
	FirestationService firestationService;

	@Before
	private void setUp() {
		residentFireController = new ResidentFireController();
		residentFireService = new ResidentFireService();
		personService = new PersonService();
		medicalRecordService = new MedicalRecordService();
		firestationService = new FirestationService();
	}

	@Test
	public void getResidentFireTest() {
		
		ResidentDTO residentFire = new ResidentDTO();
		ArrayList<String> residents = new ArrayList<>();
		Util util = new Util();

		ArrayList<Person> persons = personService.getPersons();
		for (Person p : persons) {
			if (p.getAddress().equals("1509 Culver St")) {
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
		residentFire.setResidents(residents);

		ArrayList<Firestation> firestations = firestationService.getFirestations();
		for (Firestation fs : firestations) {
			if (fs.getAddress().equals("1509 Culver St")) {
				residentFire.setStationNumber(fs.getStationNumber());
			}
		}
		
		when(residentFireService.getResidentFire("1509 Culver St")).thenReturn(residentFire);

		ResidentDTO getResidentFire = residentFireController.getResidentFire("1509 Culver St");
		
		assertThat(getResidentFire).isEqualTo(residentFire);
	}

	@Test
	public void getNoResidentFireTest() {

		when(residentFireService.getResidentFire("1509 Culver St")).thenReturn(null);

		ResidentDTO getResidentFire = residentFireController.getResidentFire("1509 Culver St");

		assertThat(getResidentFire).isEqualTo(null);
	}



}
