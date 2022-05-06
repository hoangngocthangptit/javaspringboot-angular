package net.javaguides.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/check")
	public String checkAPI() {
		return "OK";
	}

	// get all employees
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String title) {
		try {
			List<Employee> employee = new ArrayList<Employee>();

			employeeRepository.findAll().forEach(employee::add);

			if (employee.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(employee, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
		Employee employee = employeeRepository.findById(id);

		if (employee != null) {
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/employees")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		try {
			Employee newEmployee = new Employee(employee.getFirstName(), employee.getLastName(), employee.getEmailId());
			employeeRepository.save(newEmployee);
			return new ResponseEntity<Employee>(newEmployee, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Employee>(new Employee(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
		Employee _employee = employeeRepository.findById(id);

		if (_employee != null) {
			_employee.setId(id);
			_employee.setFirstName(employee.getFirstName());
			_employee.setLastName(employee.getLastName());
			_employee.setEmailId(employee.getEmailId());

			employeeRepository.update(_employee);
			return new ResponseEntity<Employee>(_employee, HttpStatus.OK);
		} else {
			return new ResponseEntity<Employee>(_employee, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Integer> deleteEmployee(@PathVariable("id") long id) {
		try {
			int result = employeeRepository.deleteById(id);
			if (result == 0) {
				return new ResponseEntity<Integer>(result, HttpStatus.OK);
			}
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
