package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Scanner;

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
public class ResidentFloodServiceTest {

	@Autowired
	private ResidentFloodService residentFloodService;

	@Autowired
	private FirestationService firestationService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	Scanner scanner;

	@Before
	private void setUp() {
		residentFloodService = new ResidentFloodService();
		firestationService = new FirestationService();
		personService = new PersonService();
		medicalRecordService = new MedicalRecordService();
	}

	@Test
	public void getResidentsByStationNumberTest() {
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

		ArrayList<ResidentDTO> getResidentFlood = residentFloodService.getResidentFlood("1 2");
		
		assertThat(getResidentFlood).isEqualTo(residentFlood);
	}
}
