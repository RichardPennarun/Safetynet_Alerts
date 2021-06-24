package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.Scanner;

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
public class ResidentFloodService {

	private static Logger logger = LogManager.getLogger(ResidentFloodService.class);

	@Autowired
	PersonService personService;

	@Autowired
	FirestationService firestationService;

	@Autowired
	MedicalRecordService medicalRecordService;
	
	Scanner scanner;

	public ArrayList<ResidentDTO> getResidentFlood(final String stationNumbers) {

		ArrayList<ResidentDTO> residentFlood = new ArrayList<>();
		ArrayList<String> addresses = new ArrayList<>();
		Util util = new Util();

		scanner = new Scanner(stationNumbers);
		ArrayList<Integer> stations = new ArrayList<Integer>();
		while (scanner.hasNextInt()) {
			stations.add(scanner.nextInt());
			logger.debug("Get stationNumbers from String");
		}

		for (Integer station : stations) {
			ArrayList<Firestation> firestations = firestationService.getFirestations();
			for (Firestation fs : firestations) {
				if (fs.getStationNumber().equals(station)) {
					logger.debug("Get address from stationNumber");
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
			residentDTO.setResidents(residents);
			residentDTO.setAddress(address);
			logger.debug("Add list of residents to DTO");

			ArrayList<Firestation> firestations = firestationService.getFirestations();
			for (Firestation fs : firestations) {
				if (fs.getAddress().equals(address)) {
					logger.debug("Get firestation number");
					residentDTO.setStationNumber(fs.getStationNumber());
				}
			}
			residentFlood.add(residentDTO);
		}
		return residentFlood;

	}

}
