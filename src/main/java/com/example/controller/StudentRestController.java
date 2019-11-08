package com.example.controller;

import java.util.NoSuchElementException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Student;
import com.example.service.ModuleService;
import com.example.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentRestController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private ModuleService moduleService;

	@GetMapping(value="/students/{id}")
	public ResponseEntity<?> show(Model model, @PathVariable("id") Integer id) {
		
		Student student = null;
		try{
			student = studentService.findOne(id).get();
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(student, HttpStatus.OK); 
	}
	
	@GetMapping(value="/students-all")
	public ResponseEntity<?> getStudents(Pageable pageable) {
		Page<Student> page = studentService.findAll(pageable);
		
		if(page.getTotalElements()==0) {
			throw new ResourceNotFoundException("N„o h· alunos cadastrados!");
		}
		return new ResponseEntity(page, HttpStatus.OK); 
	}
	
	@DeleteMapping(value="/students/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
		studentService.deleteById(id);
		return new ResponseEntity(HttpStatus.OK); 
	}
	
	@PostMapping(value="/students/{id}")
	public ResponseEntity<?> create(@Valid @RequestBody Student entityStudent){
		
		Student student = null;
		try{
			student = studentService.save(entityStudent);
		}catch(Throwable e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(student, HttpStatus.OK);
	}
	
	private static final String MSG_SUCESS_INSERT = "Student inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Student successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Student successfully.";
	private static final String MSG_ERROR = "Erro na inser√ß√£o do Student";


}
