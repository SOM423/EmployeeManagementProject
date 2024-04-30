package com.emp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long>{

}
