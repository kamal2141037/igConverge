package com.infogain.igconverge.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.igconverge.model.Employee;
import com.infogain.igconverge.repository.EmployeeRepository;
import com.infogain.igconverge.util.IGConvergeLogger;

@Service("employeeService")
public class EmployeeService implements EmployeeHandler {

	@Autowired
	EmployeeRepository employeeRepository;

	@IGConvergeLogger
	Logger logger;

	public boolean checkAdminOrNot(String employeeId) {

		Employee fetchedEmployee;
		logger.debug("empid in emp service recieved from AdminController  = "
				+ employeeId);

		fetchedEmployee = employeeRepository
				.findEmployeeByid(employeeId);
		logger.debug("Fetched Employee = " + fetchedEmployee);

		if (fetchedEmployee != null) {
			logger.debug("admin emp found\nReturning true");
			return true;
		} else {
			logger.debug("admin emp Not found\nReturning false");
			return false;
		}
	}

}
