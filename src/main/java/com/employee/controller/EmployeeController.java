package com.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.employee.dao.EmployeeDaoImpl;
import com.employee.model.Employee;

@Controller
@SessionAttributes("employeeCount")
public class EmployeeController {
	
	@Autowired
	private EmployeeDaoImpl daoImpl;
	private static long employeeCount;
	private int pagecount;
	private static String sortType;
		
		
	@GetMapping("/home")
	public String showHome(Model model, @RequestParam(defaultValue ="") String page, @RequestParam(defaultValue ="") String sortBy) {
		Pageable pageable=null;
		if(!page.isEmpty()) {
			if(!sortBy.isEmpty()) {
				sortType = sortBy;
				pageable = PageRequest.of(Integer.parseInt(page)-1, 5,Sort.by(sortBy));
			}
			else if(sortType!=null) {
				pageable = PageRequest.of(Integer.parseInt(page)-1, 5,Sort.by(sortType));
			} else {
				pageable = PageRequest.of(Integer.parseInt(page)-1, 5);
			}
		}else if(!sortBy.isEmpty()){
			sortType = sortBy;
			pageable = PageRequest.of(0, 5,Sort.by(sortType));
		}
		else{
			employeeCount = daoImpl.count();
			pageable = PageRequest.of(0, 5);
		}
		updatePageNo();
		List<Employee> employeeList =  (List<Employee>) daoImpl.getAllEmployee(pageable);
		model.addAttribute("employees", employeeList);
		model.addAttribute("pageCount", pagecount);
		return "home";
	}

	private void updatePageNo() {
		pagecount = (int)(employeeCount%5==0?employeeCount/5:(employeeCount/5)+1);
	}

	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("id") int id) {
		daoImpl.deleteEmployee(id);
		return "redirect:home";
	}
	@GetMapping("/edit")
	public String showModifyPage(@RequestParam int id,Model model) {
		Employee employee = daoImpl.findEmployee(id);
		model.addAttribute("modifiedEmp",employee);
		return "modifyPage";
	}
	@PostMapping("/update")
	public String updateDetails(Employee employee) {
		if(employee!=null) {
			daoImpl.saveEmployee(employee);
		}
		return "redirect:home";
	}
	
	@GetMapping("/addEmployee")
	public String showAddEmployeePage(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee",employee);
		return "saveEmployee";
	}
	
	@PostMapping("/save")
	public String saveEmployee(Employee employee) {
		daoImpl.saveEmployee(employee);
		employeeCount++;
		updatePageNo();
		return "redirect:home";
	}
	
	
	@ModelAttribute
	public void populateOptions(Model model){
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("name");
		arrayList.add("age");
		arrayList.add("designation");
		model.addAttribute("options", arrayList);
	}
}
