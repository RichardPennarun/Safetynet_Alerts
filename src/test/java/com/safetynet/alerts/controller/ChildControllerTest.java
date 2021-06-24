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

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DTO.ChildDTO;
import com.safetynet.alerts.service.ChildService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonInfoService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.Util;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ChildControllerTest {

	@Autowired
	private ChildController childController;

	@MockBean
	@Autowired
	private ChildService childService;
	
	@Autowired
	private PersonService personService;

	@Autowired
	MedicalRecordService medicalRecordService;

	@Before
	private void setUp() {
		childController = new ChildController();
		childService = new ChildService();
		personService = new PersonService();
		medicalRecordService = new MedicalRecordService();
	}

	@Test
	public void getChildrenAtAddressTest() {
		ArrayList<ChildDTO> children = new ArrayList<>();
		Util util = new Util();

		ArrayList<Person> persons = personService.getPersons();
		ArrayList<Person> personsAtAddress = new ArrayList<>();
		for (Person p : persons) {
			if (p.getAddress().equals("1509 Culver St")) {
				personsAtAddress.add(p);
			}
		}

		for (Person personAtAddress : personsAtAddress) {
			ArrayList<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
			for (MedicalRecord mr : medicalRecords) {

				if(personAtAddress.getId() == mr.getId() && util.getAge(mr.getBirthdate()) <= 18) {
					
					ChildDTO child = new ChildDTO();
					ArrayList<String> coresidents = new ArrayList<String>();
					child.setId(mr.getId());
					child.setFirstName(mr.getFirstName());
					child.setLastName(mr.getLastName());
					child.setAge(util.getAge(mr.getBirthdate()));
					

					for (Person p2 : persons) {
						if (!child.getFirstName().equals(p2.getFirstName())
								&& child.getLastName().equals(p2.getLastName())
								&& p2.getAddress().equals("1509 Culver St")) {
							coresidents.add("" + p2.getFirstName() + " " + p2.getLastName() + "");
						}
						child.setCoresidents(coresidents);
					}
					children.add(child);
					
				}
			}
		}
		
		when(childService.getChildren("1509 Culver St")).thenReturn(children);

		ArrayList<ChildDTO> getChildren = childController.getChildrenAtAddress("1509 Culver St");
		
		assertThat(getChildren).isEqualTo(children);
	}

	@Test
	public void getNoChildrenAtAddressTest() {

		when(childService.getChildren("1509 Culver St")).thenReturn(null);

		ArrayList<ChildDTO> getChildren = childController.getChildrenAtAddress("1509 Culver St");

		assertThat(getChildren).isEqualTo(null);
	}



}
