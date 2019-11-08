package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Book;
import com.example.model.Student;
import com.example.repository.StudentRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public List<Student> findAll() {
		return studentRepository.findAll();
	}
	
	public Page<Student> findAll(Pageable pageable) {
		return studentRepository.findAll(pageable);
	}
	
	public Optional<Student> findOne(Integer id) {
		return studentRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Student save(Student entity) {
		return studentRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Student entity) {
		studentRepository.delete(entity);
	}
	
	@Transactional(readOnly = false)
	public void deleteById(Integer id) {
		studentRepository.deleteById(id);;
	}

}
	
