package com.safetynet.alerts.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.DTO.PersonInfoDTO;
import com.safetynet.alerts.service.PersonInfoService;

@RestController
public class PersonInfoController {

	private static Logger logger = LogManager.getLogger(PersonInfoController.class);

	@Autowired
	PersonInfoService personInfoService;

	// Get PersonInfo, @Param - firstname,lastname, @Return - a personInfo object
	@GetMapping("/personInfo")
	public PersonInfoDTO getPersonInfo(@RequestParam("firstname") final String firstname,
			@RequestParam("lastname") final String lastname) {
		try {
			logger.info("Request - Person info with firstname=" + firstname + ", lastname=" + lastname);
			PersonInfoDTO personInfo = personInfoService.getPersonInfo(firstname, lastname);
			if (personInfo.getId() == 0) {
				logger.error("Wrong entry, " + firstname + " " + lastname + " not in DB");
			} else {
				logger.info("Response - Infos for " + firstname + " " + lastname + "=" + personInfo);
				return personInfo;
			}
			return null;
		} catch (Exception e) {
			logger.error("Unable to process request", e);
		}
		return null;

	}

}
