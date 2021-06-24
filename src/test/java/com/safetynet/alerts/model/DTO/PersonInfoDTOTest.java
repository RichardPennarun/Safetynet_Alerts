package com.safetynet.alerts.model.DTO;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.jupiter.api.Test;


public class PersonInfoDTOTest {
	
	
	@Before
	private void setUpPerTest() {
		
	}
	
	@Test
	public void getPersonInfo() {
		
		PersonInfoDTO pi = new PersonInfoDTO();
		pi.setId(2);
		pi.setFirstName("richard");
		pi.setLastName("pennarun");
		pi.setAddress("36 rue des envierges");
		pi.setCity("paris");
		pi.setZip("75020");
		pi.setAge(24);
		pi.setEmail("richard@mail.com");
		pi.setMedications("Doliprane");
		pi.setAllergies(null);

		assertThat(pi.getId()).isEqualTo(2);
		assertThat(pi.getFirstName()).isEqualTo("richard");
		assertThat(pi.getLastName()).isEqualTo("pennarun");
		assertThat(pi.getAddress()).isEqualTo("36 rue des envierges");
		assertThat(pi.getCity()).isEqualTo("paris");
		assertThat(pi.getZip()).isEqualTo("75020");
		assertThat(pi.getAge()).isEqualTo(24);
		assertThat(pi.getEmail()).isEqualTo("richard@mail.com");
		assertThat(pi.getMedications()).isEqualTo("Doliprane");
		assertThat(pi.getAllergies()).isEqualTo(null);
	}

}
