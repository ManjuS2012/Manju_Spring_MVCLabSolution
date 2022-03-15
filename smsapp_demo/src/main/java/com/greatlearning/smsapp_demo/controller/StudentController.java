package com.greatlearning.smsapp_demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greatlearning.smsapp_demo.entity.Student;
import com.greatlearning.smsapp_demo.service.StudentService;



@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	public StudentController()
	{
		System.out.println("Student controller start");
	}

	// add mapping for "/list"

	@GetMapping("/list")
	@Transactional
	public String listBooks(Model theModel) {

		System.out.println("request recieved");
		// get Books from db
		List<Student> theStudents = studentService.findAll();
		System.out.println("Inside controller list" );

		// add to the spring model
		theModel.addAttribute("Students", theStudents);
		System.out.println("Students " + theStudents);
		return "list-Students";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Student theStudent = new Student();

		theModel.addAttribute("Student", theStudent);

		return "Student-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentId") int theId,
			Model theModel) {

		// get the Book from the service
		Student theStudent = studentService.findById(theId);


		// set Book as a model attribute to pre-populate the form
		theModel.addAttribute("Student", theStudent);

		// send over to our form
		return "Student-form";			
	}


	@PostMapping("/save")
	public String saveBook(@RequestParam("id") int id,
			@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestParam("course") String course,@RequestParam("country") String country) {

		System.out.println(id);
		Student theStudent;
		if(id!=0)
		{
			theStudent=studentService.findById(id);
			theStudent.setFirstName(firstName);
			theStudent.setLastName(lastName);
			theStudent.setCourse(course);
			theStudent.setCountry(country);
		}
		else
			theStudent=new Student(firstName, lastName, course,country);
		// save the Book
		studentService.save(theStudent);


		// use a redirect to prevent duplicate submissions
		return "redirect:/student/list";

	}

	@GetMapping("/delete")
	public String delete(@RequestParam("studentId") int theId) {

		// delete the Book
		studentService.deleteById(theId);

		// redirect to /Books/list
		return "redirect:/student/list";

	}
}