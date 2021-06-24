package com.safetynet.alerts.repository.DTO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.DTO.PersonInfoDTO;

@Repository
public interface PersonInfoDTORepository extends CrudRepository <PersonInfoDTO, Integer>{

}
