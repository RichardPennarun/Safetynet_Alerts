package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.alerts.model.DTO.PersonInfoDTO;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonInfoServiceTest {
	
	@Autowired
	private PersonInfoService personInfoService;
	
	@MockBean
	@Autowired
	private PersonInfoDTO personInfo;
	
	@Before
	private void setUp() {
		personInfoService = new PersonInfoService();
		personInfo = new PersonInfoDTO();
	}

	@Test
	public void getPersonInfoTest() {
		
		personInfo = personInfoService.getPersonInfo("John", "Boyd");

		assertThat(personInfo.getId()).isEqualTo(1);
		assertThat(personInfo.getFirstName()).isEqualTo("John");
		assertThat(personInfo.getLastName()).isEqualTo("Boyd");
		assertThat(personInfo.getAddress()).isEqualTo("1509 Culver St");
		assertThat(personInfo.getCity()).isEqualTo("Culver");
		assertThat(personInfo.getZip()).isEqualTo("97451");
		assertThat(personInfo.getAge()).isEqualTo(37);
		assertThat(personInfo.getEmail()).isEqualTo("jaboyd@email.com");
		assertThat(personInfo.getMedications()).isEqualTo("aznol:350mg, hydrapermazol:100mg");
		assertThat(personInfo.getAllergies()).isEqualTo("nillacilan");

	}

}
