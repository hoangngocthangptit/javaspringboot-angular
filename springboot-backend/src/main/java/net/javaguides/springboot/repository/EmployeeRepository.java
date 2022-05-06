package net.javaguides.springboot.repository;
import java.util.List;


import net.javaguides.springboot.model.Employee;

public interface EmployeeRepository {
	  int save(Employee employee);

	  int update(Employee employee);

	  Employee findById(Long id);

	  int deleteById(Long id);

	  List<Employee> findAll();
}
