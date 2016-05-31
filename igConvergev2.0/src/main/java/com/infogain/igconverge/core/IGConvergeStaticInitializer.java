package com.infogain.igconverge.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infogain.igconverge.service.EmployeeHandler;
import com.infogain.igconverge.util.IGConvergeUtil;

@Component("igConvergeStaticInitializer")
public class IGConvergeStaticInitializer{

	@Autowired
	EmployeeHandler employeeHandler;
	
	@PostConstruct
	public void postConstruct() {
		IGConvergeUtil.employeeHandler = employeeHandler;
	}
}
