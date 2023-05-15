package com.employee.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.employee.model.Employee;

@Service
public class EmployeeDaoImpl {

	@Autowired
	private EmployeeDaoService daoService;
	
	public void deleteEmployee(int id) {
		daoService.deleteById(id);
		System.out.println("Employee Deleted");
	}
	
	public List<Employee> getAllEmployee(Pageable pageable){
		Page<Employee> employees = daoService.findAll(pageable);
		return employees.toList();
	}
	
	public Employee findEmployee(int id) {
	 Optional<Employee> employee = daoService.findById(id);
	 if(employee.isPresent()) {
		 return employee.get();
	 }else {
		 return null;
	 }
	}
	
	public Employee saveEmployee(Employee employee) {
		Employee emp = daoService.save(employee);
		return emp;
	}
	
	public List<Employee> sortData(String sort){
		List<Employee> employeeList = (List<Employee>) daoService.findAll(Sort.by(Sort.DEFAULT_DIRECTION,sort));
		return employeeList;
	}
	
	public long count() {
		return daoService.count();
	}
}
