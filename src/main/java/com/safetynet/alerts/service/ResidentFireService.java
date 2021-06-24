package com.safetynet.alerts.service;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DTO.ResidentDTO;
import com.safetynet.alerts.util.Util;

@Service
public class ResidentFireService {
	
	private static Logger logger = LogManager.getLogger(ResidentFireService.class);

	@Autowired
	PersonService personService;

	@Autowired
	FirestationService firestationService;

	@Autowired
	MedicalRecordService medicalRecordService;

	public ResidentDTO getResidentFire(final String address) {

		ResidentDTO residentFire = new ResidentDTO();
		ArrayList<String> residents = new ArrayList<>();
		Util util = new Util();

		ArrayList<Person> persons = personService.getPersons();
		for (Person p : persons) {
			if (p.getAddress().equals(address)) {
				String resident = new String();
				logger.debug("Get infos about resident in model Person");

				ArrayList<MedicalRecord> medicalrecords = medicalRecordService.getMedicalRecords();
				for (MedicalRecord mr : medicalrecords) {
					if (mr.getFirstName().equals(p.getFirstName())
							&& mr.getLastName().equals(p.getLastName())) {
						logger.debug("Create a resident");

						resident = "" + p.getFirstName() + " " + p.getLastName() + ", " + p.getPhone() + ", "
							+ util.getAge(mr.getBirthdate()) + ", " + mr.getMedications() + ", " + mr.getAllergies() + "";
					}
				}
				residents.add(resident);
				logger.debug("Add resident to list");
			}
		}
		residentFire.setResidents(residents);
		residentFire.setAddress(address);
		logger.debug("Add list of residents to DTO");

		ArrayList<Firestation> firestations = firestationService.getFirestations();
		for (Firestation fs : firestations) {
			if (fs.getAddress().equals(address)) {
				logger.debug("Get firestation number");
				residentFire.setStationNumber(fs.getStationNumber());
			}
		}
		return residentFire;
	}

}
