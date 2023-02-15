package com.taxcalculator.controller;

import com.taxcalculator.model.EmployeeDetails;
import com.taxcalculator.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jdk.internal.vm.compiler.word.LocationIdentity.any;

@RestController
public class TaxCalculatorController {

	private final EmployeeRepository employeeRepository;

	@Autowired
	public TaxCalculatorController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@RequestMapping(value = "/insertEmployeeDetails", method = RequestMethod.POST, produces = "application/json")
	public EmployeeDetails insertEmployeeDetails(@RequestBody EmployeeDetails employeeDetails) {
		return employeeRepository.save(employeeDetails);
	}

	@RequestMapping(value = "/getTaxDetails", method = RequestMethod.GET, produces = "application/json")
	public List<Map<String,Object>> insertEmployeeDetails(@RequestBody EmployeeDetails employeeDetails) {
		List<EmployeeDetails> employeeDetails1 = employeeRepository.findAll();
		List<Map<String,Object>> taxDetails=new ArrayList<Map<String, Object>>();


		for (EmployeeDetails employeeDetails2 : employeeDetails1)
		{
			float salary=0;
			float annualSalary=0;
			int empId=0;
			double tax=0;
			Map<String,Object> maps = new HashMap();
			salary=employeeDetails2.getSalary();
			annualSalary=salary*12;
			empId=employeeDetails2.getEmployeeId();
			tax=calculateTax(annualSalary);
			maps.put("employeeID", empId);
			maps.put("annualSalary", annualSalary);
			maps.put("salary", salary);
			maps.put("tax", tax);
			taxDetails.add(maps);
		}
		return taxDetails;
	}

	public double calculateTax(double income) {
		double tax = 0;
		double appIncome = 0;

		if (income <= 250000) {
			tax = 0;
		} else if (income >= 250001 && income <= 500000) {
			appIncome = income - 250000;
			tax = 0.05 * appIncome;
		} else if (income >= 500001 && income <= 1000000) {
			appIncome = income - 500000;
			tax = 12500 + (0.20 * appIncome);
		} else { // income > 1000000
			appIncome = income - 1000000;
			tax = 112500 + (0.30 * appIncome);
		}

		return tax;
	}

}
