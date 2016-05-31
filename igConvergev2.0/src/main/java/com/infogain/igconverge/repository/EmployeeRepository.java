package com.infogain.igconverge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.infogain.igconverge.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Integer> {

	public Employee findEmployeeByid(String employeeId);
	
	public Employee findEmployeeByname(String employeeName);

}
