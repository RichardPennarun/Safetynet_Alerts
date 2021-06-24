package com.safetynet.alerts.controller;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DTO.ChildDTO;
import com.safetynet.alerts.service.ChildService;
import com.safetynet.alerts.service.PersonService;

@RestController
public class ChildController {

	private static Logger logger = LogManager.getLogger(ChildController.class);

	@Autowired
	ChildService childService;

	@Autowired
	PersonService personService;

	// Get children at address, @Param - an address String, @Retrun - a list of
	// children objects
	@GetMapping("/childAlert")
	public ArrayList<ChildDTO> getChildrenAtAddress(@RequestParam("address") final String address) {
		logger.info("Request - Children at address: " + address);

		boolean addressOK = false;
		ArrayList<Person> persons = personService.getPersons();
		ArrayList<String> addresses = new ArrayList<>();
		for (Person p : persons) {
			addresses.add(p.getAddress());
		}
		for (String a : addresses) {
			if (a.equals(address)) {
				addressOK = true;
			}
		}
		
		try {
			if (addressOK == true) {
				ArrayList<ChildDTO> children = childService.getChildren(address);
				logger.info("Response - Children at address " + address + ": " + children);
				return children;
				// Comme demandé on renvoie un fichier vide si aucun enfant dans le foyer
			} else {
				logger.info("No such address in DB: " + address);
				return null;
			}
		} catch (Exception e) {
			logger.error("Unable to process request", e);
			return null;
		}
	}
}
