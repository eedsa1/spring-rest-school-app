package com.example.model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;



@Entity
@Table(name = "modules")
public class Module implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;
	
    @Override
	public String toString() {
		return name;
	}
    
    @JsonBackReference
    @OneToMany(mappedBy="module", cascade = CascadeType.ALL)
    List<Student> students;
    
    @JsonBackReference
    @OneToMany(mappedBy="module", cascade = CascadeType.ALL)
    List<Book> books;
    
	public void setCode(String code) {this.code = code;}
	public String getCode() {return code;}
	
	public void setName(String name) {this.name = name;}
	public String getName() {return name;}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	
	
}