package com.koushal.springSecurity.SpringSecurityRevision.controllers;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {
	
	List<Todo> todos= new ArrayList<>();
	{
		todos.add(new Todo("koushal","learn spring boot"));
		todos.add(new Todo("koushal2","learn java"));
	}
	@GetMapping(value = "/todos")
	public List<Todo> getHelloWorld() {
		return todos;
	}

	@PostMapping(value = "/todos")
	public void addTodo(@RequestBody Todo todo){
		todos.add(todo);
	}

	@GetMapping(value = "/csrf-token")
	public CsrfToken getCSRF(HttpServletRequest request){
		return (CsrfToken) request.getAttribute("_csrf");
	}
	
	
}

record Todo(String username,String description) {}