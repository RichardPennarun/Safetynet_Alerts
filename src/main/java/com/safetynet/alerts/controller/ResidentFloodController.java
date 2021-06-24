package com.safetynet.alerts.controller;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.DTO.ResidentDTO;
import com.safetynet.alerts.service.ResidentFloodService;

@RestController
public class ResidentFloodController {

	private static Logger logger = LogManager.getLogger(ResidentFloodController.class);

	@Autowired
	ResidentFloodService residentFloodService;

	// Get residents by address for a list of firestation numbers, @Param - a
	// stationnumber list,
	// @Return - a list of objets (Resident by address) by station
	@GetMapping("/flood/stations")
	public ArrayList<ResidentDTO> getResidentFlood(
			@RequestParam("station") final String stationNumbers) {
		try {
		logger.info("Request - Residents for stationNumber " + stationNumbers);
		ArrayList<ResidentDTO> residentFlood = (ArrayList<ResidentDTO>) 
				residentFloodService.getResidentFlood(stationNumbers);
		return residentFlood;
		// On renvoie un fichier vide si aucun des noms de caserne n'existe
		} catch (Exception e) {
			logger.error("Unable to process request", e);
		}
		return null;
	}

}
