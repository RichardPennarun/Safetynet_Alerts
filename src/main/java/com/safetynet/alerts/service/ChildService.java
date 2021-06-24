package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DTO.ChildDTO;
import com.safetynet.alerts.util.Util;


@Service
public class ChildService {

	private static Logger logger = LogManager.getLogger(ChildService.class);

	@Autowired
	PersonService personService;

	@Autowired
	MedicalRecordService medicalRecordService;


	public ArrayList<ChildDTO> getChildren(String address) {

		ArrayList<ChildDTO> children = new ArrayList<>();
		Util util = new Util();

		ArrayList<Person> persons = personService.getPersons();
		ArrayList<Person> personsAtAddress = new ArrayList<>();
		for (Person p : persons) {
			if (p.getAddress().equals(address)) {
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
								&& p2.getAddress().equals(address)) {
							coresidents.add("" + p2.getFirstName() + " " + p2.getLastName() + "");
						}
						child.setCoresidents(coresidents);
					}
					children.add(child);
					
				}
			}
		}
		
		return children;
	}
	
}
