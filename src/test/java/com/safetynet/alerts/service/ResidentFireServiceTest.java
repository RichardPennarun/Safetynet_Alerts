package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DTO.ResidentDTO;
import com.safetynet.alerts.util.Util;

@SpringBootTest
public class ResidentFireServiceTest {

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
		residentFireService = new ResidentFireService();
		personService = new PersonService();
		medicalRecordService = new MedicalRecordService();
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

		ResidentDTO getResidentFire = residentFireService.getResidentFire("1509 Culver St");

		assertThat(getResidentFire).isEqualTo(residentFire);

	}
}
