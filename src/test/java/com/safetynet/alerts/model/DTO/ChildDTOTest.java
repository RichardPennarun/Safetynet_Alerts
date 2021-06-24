package com.safetynet.alerts.model.DTO;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ChildDTOTest {
	
	
	@Before
	private void setUpPerTest() {
		
	}
	
	@Test
	public void getChildDTO() {
		
		ChildDTO child = new ChildDTO();
		child.setId(5);
		child.setFirstName("richard");
		child.setLastName("pennarun");
		child.setAge(24);
		child.setCoresidents(null);

		assertThat(child.getId()).isEqualTo(5);
		assertThat(child.getFirstName()).isEqualTo("richard");
		assertThat(child.getLastName()).isEqualTo("pennarun");
		assertThat(child.getAge()).isEqualTo(24);
		assertThat(child.getCoresidents()).isEqualTo(null);
	}

}
