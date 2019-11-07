package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.Book;
import com.example.model.Module;
import com.example.model.Student;
import com.example.service.BookService;
import com.example.service.ModuleService;
import com.example.service.StudentService;

@Controller
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private ModuleService moduleService; //module service
	
	@GetMapping
	public String index(Model model) {
		List<Book> all = bookService.findAll();
		model.addAttribute("listBook", all);
		model.addAttribute("");
		return "book/index";
	}
	
	// Tela de Show Student
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Book book = bookService.findOne(id).get();
			model.addAttribute("book", book);
		}
		return "book/show";
	}

	// Tela com Formulario de New Student
	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Book entityBook, 
			             @ModelAttribute Module entityModule) {
		// model.addAttribute("student", entityStudent);
		List<Module> all = moduleService.findAll();
		model.addAttribute("modules", all);
		
		return "book/form";
	}
	
	// Processamento do formulario New Student (ou Alter Student) 
	@PostMapping
	public String create(@Valid @ModelAttribute Book entityStudent, 
			             @Valid @ModelAttribute Module entityModule,
			             BindingResult result, RedirectAttributes redirectAttributes) {
		Book book = null;
		String pagina_retorno = "redirect:/books/" ;
	
		try {
			book = bookService.save(entityStudent);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
			pagina_retorno = pagina_retorno + book.getId();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}catch (Throwable e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		
		try {
			if (id != null) {
				List<Module> all = moduleService.findAll();
				model.addAttribute("modules", all);
				
				Book entity = bookService.findOne(id).get();
				model.addAttribute("book", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "book/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Book entity, BindingResult result, 
			             RedirectAttributes redirectAttributes) {
		Book book = null;
		try {
			book = bookService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/books/" + book.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Book entity = bookService.findOne(id).get();
				bookService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/books/";
	}
	
	private static final String MSG_SUCESS_INSERT = "Book inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Book successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted book successfully.";
	private static final String MSG_ERROR = "Erro na inserção do Book";
	
}
