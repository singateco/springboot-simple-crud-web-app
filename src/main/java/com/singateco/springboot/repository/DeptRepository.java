package com.singateco.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.singateco.springboot.model.Dept;


public interface DeptRepository extends JpaRepository<Dept, Long>{
	
}
