package com.safetynet.alerts.model.DTO;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ResidentDTOTest {
	
	
	@Before
	private void setUpPerTest() {
		
	}
	
	@Test
	public void getResidentDTO() {
		
		ResidentDTO residentByAddress = new ResidentDTO();
		residentByAddress.setId(2);
		residentByAddress.setStationNumber(2);
		residentByAddress.setAddress("36 rue des envierges");
		residentByAddress.setResidents(null);

		assertThat(residentByAddress.getId()).isEqualTo(2);
		assertThat(residentByAddress.getStationNumber()).isEqualTo(2);
		assertThat(residentByAddress.getAddress()).isEqualTo("36 rue des envierges");
		assertThat(residentByAddress.getResidents()).isEqualTo(null);
	}

}
