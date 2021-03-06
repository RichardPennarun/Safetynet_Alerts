package com.safetynet.alerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class AlertsApplication {
	
	private static final Logger logger = LogManager.getLogger(AlertsApplication.class);

	public static void main(String[] args) {
		logger.info("Initializing SafetynetAlerts");
		SpringApplication.run(AlertsApplication.class, args);
	}

}
