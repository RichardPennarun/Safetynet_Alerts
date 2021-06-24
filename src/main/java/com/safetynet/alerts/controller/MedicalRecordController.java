package com.safetynet.alerts.controller;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

	private static Logger logger = LogManager.getLogger(MedicalRecordController.class);

	@Autowired
	MedicalRecordService medicalRecordService;

	// Get all medicalRecords, returns a list of all medicalRecords
	@GetMapping("/medicalrecords")
	public ArrayList<MedicalRecord> getMedicalRecords() {
		try {
			logger.info("Request - Get all medicalRecords");
			ArrayList<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
			logger.info("Response - Returns all medicalRecords: " + medicalRecords);
			return medicalRecords;
		} catch (Exception e) {
			logger.error("Unable to process request", e);
		}
		return null;
	}

	// Add a medicalRecord, @param an object medicalRecord, @return - The
	// medicalRecord object added
	@PostMapping("/medicalrecord")
	public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		try {
			logger.info("Request - Create medicalRecord @RequestBody = {} ", medicalRecord);
			MedicalRecord createdMedicalRecord = medicalRecordService.saveMedicalRecord(medicalRecord);
			if (createdMedicalRecord.getFirstName() == "" || createdMedicalRecord.getLastName() == "") {
				logger.error("Wrong entry, missing first/last name: " + medicalRecord);
				return null;
			} else {
				logger.info("Response - medicalRecord saved: " + createdMedicalRecord);
				return createdMedicalRecord;
			}
		} catch (Exception e) {
			logger.error("Unable to process request", e);
		}
		return null;
	}

	// Update existing medicalRecord, @param the modified medicalRecord
	// (firstname/lastname can't be modified)
	// @return the updated medicalRecord
	@PutMapping("/medicalrecord")
	public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		try {
			logger.info("Request - Update medicalRecord @RequestBody = {} ", medicalRecord);
			MedicalRecord updatedMedicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord);
			logger.info("Response - medicalRecord updated: " + updatedMedicalRecord);
			return updatedMedicalRecord;
		} catch (Exception e) {
			logger.error("Unable to process request", e);
		}
		return null;
	}

	// Delete a medicalRecord by combination firstname/lastname, @param
	// firstname/lastname
	@DeleteMapping("/medicalrecord")
	public void deleteMedicalRecord(@RequestParam("firstname") final String firstname,
			@RequestParam("lastname") final String lastname) {
		try {
			logger.info("Request - Delete medical record with firstname {}, lastname {}", firstname, lastname);
			medicalRecordService.deleteMedicalRecord(firstname, lastname);
		} catch (Exception e) {
			logger.error("Unable to process request", e);
		}
	}

}
