package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DTO.ChildDTO;
import com.safetynet.alerts.util.Util;

@SpringBootTest
public class ChildServiceTest {

	@Autowired
	private ChildService childService;

	@Autowired
	private PersonService personService;

	@Autowired
	MedicalRecordService medicalRecordService;

	@Before
	private void setUp() {
		childService = new ChildService();
		personService = new PersonService();
		medicalRecordService = new MedicalRecordService();
	}

	@Test
	public void getChildrenTest() {
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

		ArrayList<ChildDTO> getChildren = childService.getChildren("1509 Culver St");
		
		assertThat(getChildren).isEqualTo(children);
	}



}
