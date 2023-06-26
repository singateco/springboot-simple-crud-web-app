package com.singateco.springboot.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.singateco.springboot.model.Dept;
import com.singateco.springboot.model.Employee;
import com.singateco.springboot.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "name", "asc", model);
	}
	
	@GetMapping("/newEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		Employee emp = new Employee();
		model.addAttribute("employee", emp);
		
		List<Dept> deptList = employeeService.getAllDept();
		model.addAttribute("deptList", deptList);
		
		return "new_employee";
	}
	
	@GetMapping("/deptManage")
	public String showDeptManagement(Model model) {
		List<Dept> deptList = employeeService.getAllDept();
		
		Dept dept = new Dept();
		model.addAttribute("dept", dept);
		
		LinkedHashMap<Dept, Long> deptNumMap = new LinkedHashMap<>();
		
		for (Dept d: deptList) {
			deptNumMap.put(d, employeeService.getEmployeeNumByDept(d));
		}
		
		model.addAttribute("deptNumMap", deptNumMap);
		
		return "dept_manage";
	}
	
	@PostMapping("/saveDept")
	public String saveDept(@ModelAttribute("dept") @Valid Dept dept, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		// 에러가 있을시 다시 폼 리로드
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("deptNameError", "부서 이름은 비어서는 안됩니다.");
			return "redirect:/deptManage";
		}
		
		// DB에 저장
		employeeService.saveDept(dept);
		return "redirect:/deptManage";
	}
	
	@GetMapping("/deleteDept/{id}")
	public String deleteDept(@PathVariable (value = "id") long id) {
		
		employeeService.deleteDeptById(id);
		
		return "redirect:/deptManage";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		// 에러가 있을시 다시 폼 리로드
		if (bindingResult.hasErrors()) {
			
			List<Dept> deptList = employeeService.getAllDept();
			model.addAttribute("deptList", deptList);
			
			return "new_employee";
		}
		
		// DB에 저장
		employeeService.saveEmployee(employee);
		redirectAttributes.addFlashAttribute("alertMsg", "직원 " + employee.getName() + "의 저장을 성공했습니다.");
		return "redirect:/";
	}
	
	@PostMapping("/saveUpdatedEmployee")
	public String saveUpdatedEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		// 에러가 있을시 다시 폼 리로드
		if (bindingResult.hasErrors()) {
			
			List<Dept> deptList = employeeService.getAllDept();
			model.addAttribute("deptList", deptList);
			
			return "update_employee";
		}
		
		// DB에 저장
		employeeService.saveEmployee(employee);
		redirectAttributes.addFlashAttribute("alertMsg", "직원 " + employee.getName() + "의 정보를 성공적으로 수정했습니다.");
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable (value = "id") long id, Model model) {
		
		Employee employee = employeeService.getEmployeeById(id);
		
		List<Dept> deptList = employeeService.getAllDept();
		model.addAttribute("deptList", deptList);
		
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id, RedirectAttributes redirectAttributes) {
		
		employeeService.deleteEmployeeById(id);
		redirectAttributes.addFlashAttribute("alertMsg", "직원을 성공적으로 삭제했습니다.");
		
		return "redirect:/";
	}
	
	@GetMapping("/page/{pageNum}")
	public String findPaginated(@PathVariable (value = "pageNum") int pageNum,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Employee> page = employeeService.findPaginated(pageNum, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", listEmployees);
		
		return "index";
	}
	
}
