package com.example.controller;

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
import com.example.model.Module;
import com.example.service.ModuleService;

@RestController
@RequestMapping("/modules")
public class ModuleRestController {
	
	@Autowired
	private ModuleService moduleService;
	
	@GetMapping(value="/modules/{id}")
	public ResponseEntity<?> show(Model model, @PathVariable("id") Integer id) {
		
		Module module = null;
		try{
			module = moduleService.findOne(id).get();
		}catch(ResourceNotFoundException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(module, HttpStatus.OK); 
	}
	
	@GetMapping(value="/modules-all")
	public ResponseEntity<?> getBooks(Pageable pageable) {
		Page<Module> page = moduleService.findAll(pageable);
		
		if(page.getTotalElements()==0) {
			throw new ResourceNotFoundException("Não há módulos cadastrados!");
		}
		return new ResponseEntity(page, HttpStatus.OK); 
	}
	
	@DeleteMapping(value="/modules/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
		moduleService.deleteById(id);
		return new ResponseEntity(HttpStatus.OK); 
	}
	
	@PostMapping(value="/modules/{id}")
	public ResponseEntity<?> create(@Valid @RequestBody Module entityModule){
		
		Module module = null;
		try{
			module = moduleService.save(entityModule);
		}catch(Throwable e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(module, HttpStatus.OK);
	}
	
	private static final String MSG_SUCESS_INSERT = "Module inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Module successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Module successfully.";
	private static final String MSG_ERROR = "Error.";
	
}
