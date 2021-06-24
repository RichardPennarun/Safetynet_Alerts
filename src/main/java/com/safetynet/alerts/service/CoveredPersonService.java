package com.safetynet.alerts.service;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DTO.CoveredPersonDTO;
import com.safetynet.alerts.util.Util;

@Service
public class CoveredPersonService {

	private static Logger logger = LogManager.getLogger(CoveredPersonService.class);

	@Autowired
	PersonService personService;

	@Autowired
	FirestationService firestationService;

	@Autowired
	MedicalRecordService medicalRecordService;

	public CoveredPersonDTO getCoveredPersons(final int stationNumber) {

		CoveredPersonDTO coveredPersonDTO = new CoveredPersonDTO();
		int adultcount = 0;
		int childcount = 0;
		Util util = new Util();
		ArrayList<String> coveredPersons = new ArrayList<>();
		ArrayList<String> addresses = new ArrayList<>();

		ArrayList<Firestation> firestations = firestationService.getFirestations();
		for (Firestation fs : firestations) {
			if (fs.getStationNumber().equals(stationNumber)) {
				addresses.add(fs.getAddress());
			}
		}

		ArrayList<Person> persons = personService.getPersons();
		for (String address : addresses) {
			for (Person p : persons) {
				if (p.getAddress().equals(address)) {

					String coveredPerson = new String();
					logger.debug("Create a coveredPerson");
					coveredPerson = "" + p.getFirstName() + " " + p.getLastName() + ", " + p.getAddress() + ", "
							+ p.getCity() + ", " + p.getZip() + ", " + p.getPhone() + "";

					ArrayList<MedicalRecord> medicalrecords = medicalRecordService.getMedicalRecords();
					for (MedicalRecord mr : medicalrecords) {
						if (mr.getFirstName().equals(p.getFirstName()) && mr.getLastName().equals(p.getLastName())) {

							if (util.getAge(mr.getBirthdate()) > 18) {
								logger.debug("Add an adult to counter");
								adultcount++;
							} else {
								logger.debug("Add a child to counter");
								childcount++;
							}
						}
					}
					logger.debug("Add CoveredPerson to list coveredPersons");
					coveredPersons.add(coveredPerson);

				}
			}
			logger.debug("Create coveredPersonDTO");
			coveredPersonDTO.setStationNumber(stationNumber);
			coveredPersonDTO.setCoveredPersons(coveredPersons);
			coveredPersonDTO.setAdults(adultcount);
			coveredPersonDTO.setChildren(childcount);
		}

		return coveredPersonDTO;
	}

}
