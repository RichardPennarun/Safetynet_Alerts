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
import com.safetynet.alerts.model.DTO.CoveredPersonDTO;
import com.safetynet.alerts.service.CoveredPersonService;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.Util;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CoveredPersonControllerTest {

	@Autowired
	private CoveredPersonController coveredPersonController;

	@MockBean
	@Autowired
	private CoveredPersonService coveredPersonService;

	@Autowired
	private FirestationService firestationService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private MedicalRecordService medicalRecordService;

	@Before
	private void setUp() {
		coveredPersonController = new CoveredPersonController();
		coveredPersonService = new CoveredPersonService();
		firestationService = new FirestationService();
		personService = new PersonService();
		medicalRecordService = new MedicalRecordService();
	}

	@Test
	public void getCoveredPersonsTest() {

		CoveredPersonDTO coveredPersonDTO = new CoveredPersonDTO();

		ArrayList<String> coveredPersons = new ArrayList<>();
		ArrayList<String> addresses = new ArrayList<>();

		ArrayList<Firestation> firestations = firestationService.getFirestations();
		for (Firestation fs : firestations) {
			if (fs.getStationNumber().equals(2)) {
				addresses.add(fs.getAddress());
			}
		}

		ArrayList<Person> persons = personService.getPersons();
		for (String address : addresses) {
			for (Person p : persons) {
				if (p.getAddress().equals(address)) {

					String coveredPerson = new String();
					coveredPerson = "" + p.getFirstName() + " " + p.getLastName() + ", " + p.getAddress() + ", "
							+ p.getCity() + ", " + p.getZip() + ", " + p.getPhone() + "";

					
					coveredPersons.add(coveredPerson);

				}
			}
			coveredPersonDTO.setStationNumber(2);
			coveredPersonDTO.setAdults(4);
			coveredPersonDTO.setChildren(1);
			coveredPersonDTO.setCoveredPersons(coveredPersons);
		}
		
		when(coveredPersonService.getCoveredPersons(2)).thenReturn(coveredPersonDTO);

		CoveredPersonDTO getCoveredPersons = coveredPersonController.getCoveredPersons(2);
		
		assertThat(getCoveredPersons).isEqualTo(coveredPersonDTO);
	}

	@Test
	public void getNoCoveredPersonsTest() {

		when(coveredPersonService.getCoveredPersons(7)).thenReturn(null);

		CoveredPersonDTO getCoveredPersons = coveredPersonController.getCoveredPersons(7);

		assertThat(getCoveredPersons).isEqualTo(null);
	}
}
