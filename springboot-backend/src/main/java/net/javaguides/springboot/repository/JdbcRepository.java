package net.javaguides.springboot.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Employee;

@Repository
public class JdbcRepository implements EmployeeRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int save(Employee employee) {
		System.out.print("sdbhjghasg");
		return jdbcTemplate.update(
				"INSERT INTO employees (email_id, first_name, last_name) VALUES(?, ?, ?)",
				new Object[] { employee.getEmailId(), employee.getFirstName(), employee.getLastName() });

	}

	@Override
	public int update(Employee employee) {
		return jdbcTemplate.update(
				"UPDATE employees SET email_id=?, first_name=?, last_name=? WHERE id=?",
				new Object[] { employee.getEmailId(), employee.getFirstName(), employee.getLastName(), employee.getId() });
	}

	@Override
	public Employee findById(Long id) {
		try {
			Employee employee = jdbcTemplate.queryForObject("SELECT * FROM employees WHERE id=?",
					BeanPropertyRowMapper.newInstance(Employee.class), id);
			return employee;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}

	}

	@Override
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("DELETE FROM employees WHERE id=?", id);
	}

	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query("SELECT * from employees", BeanPropertyRowMapper.newInstance(Employee.class));
	}

}
