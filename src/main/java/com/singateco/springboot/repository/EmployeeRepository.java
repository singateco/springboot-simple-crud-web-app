package com.singateco.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.singateco.springboot.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	@Query(value = "SELECT COUNT(*) FROM Employee e WHERE e.dept = :deptName")
	public long countByDept(@Param("deptName") String deptName);
}
