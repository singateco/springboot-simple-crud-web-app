package com.singateco.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.singateco.springboot.model.Dept;
import com.singateco.springboot.model.Employee;

public interface EmployeeService {
	List<Employee> getAllEmployees();
	void saveEmployee(Employee employee);
	Employee getEmployeeById(long id);
	void deleteEmployeeById(long id);
	Page<Employee> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection);
	
	List<Dept> getAllDept();
	long getEmployeeNumByDept(Dept dept);
	void saveDept(Dept dept);
	void deleteDeptById(long id);
}
