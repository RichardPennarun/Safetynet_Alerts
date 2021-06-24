package com.safetynet.alerts.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.DTO.ResidentDTO;
import com.safetynet.alerts.service.ResidentFireService;

@RestController
public class ResidentFireController {

	private static Logger logger = LogManager.getLogger(ResidentFireController.class);

	@Autowired
	ResidentFireService residentFireService;

	// Get residents at a given address (fire alert), @Param - an address String
	// @Return - a list of Resident object, and a firestation object
	@GetMapping("/fire")
	public ResidentDTO getResidentFire(@RequestParam("address") final String address) {
		try {
			logger.info("Request - Residents at adddress" + address);
			ResidentDTO residentFire = residentFireService.getResidentFire(address);
			if (residentFire.getStationNumber() == 0) {
				logger.error("Wrong entry, address not in DB: " + address);
			} else {
				logger.info("Response - Residents at address " + address + ": " + residentFire);
				return residentFire;
			}
			return null;
		} catch (Exception e) {
			logger.error("Unable to process request", e);
		}
		return null;
	}

}
