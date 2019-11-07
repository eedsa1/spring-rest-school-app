package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Book;
import com.example.model.Student;
import com.example.repository.BookRepository;
import com.example.repository.StudentRepository;

@Service
@Transactional(readOnly = true)
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> findAll() {
		return bookRepository.findAll();
	}
	
	public Page<Book> findAll(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}
	
	public Optional<Book> findOne(Integer id) {
		return bookRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Book save(Book entity) {
		return bookRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Book entity) {
		bookRepository.delete(entity);
	}
	
	@Transactional(readOnly = false)
	public void deleteById(Integer id) {
		bookRepository.deleteById(id);;
	}

}
