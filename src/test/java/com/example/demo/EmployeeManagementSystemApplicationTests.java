package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.employee.dao.EmployeeDaoImpl;
import com.employee.model.Employee;

@SpringBootTest
class EmployeeManagementSystemApplicationTests {
	
	@Autowired
	EmployeeDaoImpl daoImpl;

	@Test
	void contextLoads() {
	}

	@Test
	void testingUpdate() {
		Employee emp = new Employee();
		emp.setAge(12);
		emp.setDesignation("qwedfgh");
		emp.setName("testing");
		
		Employee empl = daoImpl.saveEmployee(emp);
		Employee employee = daoImpl.findEmployee(empl.getId());
		employee.setDesignation("halawaaa"); 
		daoImpl.saveEmployee(employee)
;		
	}
}
