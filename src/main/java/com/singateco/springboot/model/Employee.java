package com.singateco.springboot.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "employees")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank(message = "이름은 반드시 입력해야 합니다.")
	@Length(min = 2, max=14, message="이름은 {min}~{max}자 사이로 입력해주세요.")
	@Column(name = "name")
	private String name;
	
	@Pattern(regexp= "^\\d+-\\d+-\\d+$", message ="'010-xxxx-xxxx'와 같은 방식으로 입력해주세요.")
	@Column(name = "phoneNum")
	private String phoneNum;
	
	@Range(min = 200, max=4000, message = "월 급여는 {min}만원에서 {max}만원 사이여야 합니다.")
	@Column(name = "salary")
	private int salary;
	
	@NotBlank(message = "부서는 반드시 입력해야 합니다.")
	@Column(name = "dept")
	private String dept;
	
	@NotBlank(message = "E-mail은 반드시 입력해야 합니다.")
	@Email(message = "E-mail의 양식이 정확하지 않습니다.")
	@Column(name = "email")
	private String email;

	
	
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
