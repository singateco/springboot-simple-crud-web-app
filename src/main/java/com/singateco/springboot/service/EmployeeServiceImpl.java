package com.singateco.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.singateco.springboot.model.Dept;
import com.singateco.springboot.model.Employee;
import com.singateco.springboot.repository.DeptRepository;
import com.singateco.springboot.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DeptRepository deptRepository;
	
	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
	}
	
	@Override
	public long getEmployeeNumByDept(Dept dept) {
		return this.employeeRepository.countByDept(dept.getName());
	}
	
	@Override
	public void deleteDeptById(long id) {
		this.deptRepository.deleteById(id);
	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		
		if(optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException("직원 id :: " + id + " 에 맞는 직원이 없음");
		}
		
		return employee;
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.employeeRepository.deleteById(id);
	}

	@Override
	public Page<Employee> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		return this.employeeRepository.findAll(pageable);
	}

	@Override
	public List<Dept> getAllDept() {
		return this.deptRepository.findAll();
	}

	@Override
	public void saveDept(Dept dept) {
		this.deptRepository.save(dept);
	}

}
