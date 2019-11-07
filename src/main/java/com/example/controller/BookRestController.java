package com.example.controller;

import java.util.List;
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

import com.example.model.Book;
import com.example.model.Student;
import com.example.service.BookService;
import com.example.service.ModuleService;
import com.example.service.StudentService;

@RestController
@RequestMapping("/books")
public class BookRestController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private ModuleService moduleService; //module service
	
	@GetMapping(value="/books/{id}")
	public ResponseEntity<?> show(Model model, @PathVariable("id") Integer id) {
		
		Book book = null;
		try{
			book = bookService.findOne(id).get();
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(book, HttpStatus.OK); 
	}
	
	@GetMapping(value="/books-all")
	public ResponseEntity<?> getBooks(Pageable pageable) {
		Page<Book> page = bookService.findAll(pageable);
		
		if(page.getTotalElements()==0) {
			//throw new ResourceNotFoundException("");
		}
		return new ResponseEntity(page, HttpStatus.OK); 
	}
	
	@DeleteMapping(value="/books/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
		bookService.deleteById(id);
		return new ResponseEntity(HttpStatus.OK); 
	}
	
	@PostMapping(value="/books/{id}")
	public ResponseEntity<?> create(@Valid @RequestBody Book entityBook){
		
		Book book = null;
		try{
			book = bookService.save(entityBook);
		}catch(Throwable e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(book, HttpStatus.OK);
	}
	

}
