package com.emp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.entity.PhoneNumber;

public interface PhoneNumberRepo extends JpaRepository<PhoneNumber, Long>{
	
	

}
