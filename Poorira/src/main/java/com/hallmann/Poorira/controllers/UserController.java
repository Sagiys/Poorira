package com.hallmann.Poorira.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hallmann.Poorira.DTOs.UserDTO;
import com.hallmann.Poorira.entities.Project;
import com.hallmann.Poorira.entities.User;
import com.hallmann.Poorira.services.UserServices;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/user")
public class UserController {

	@Autowired 
	private UserServices userServices;
	
	@PostMapping(path = "/register")
	public ResponseEntity<String> addNewUser(@RequestBody User user) {
		return userServices.addNewUser(user);
	}
	
	@GetMapping(path = "/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username){
		return userServices.getUser(username);
	}
	
	@DeleteMapping(path = "/delete/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable String username){
		return userServices.deleteUser(username);
	}
	
	@PutMapping("/update/{username}")
	public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody UserDTO user){
		return userServices.editUser(username, user);
	}
	
	@GetMapping(path = "/{username}/projects")
	public ResponseEntity<List<Project>> getUserProjects(@PathVariable String username){
		return userServices.getUserProjects(username);
	}
	
	@GetMapping(path = "/all")
	public ResponseEntity<Iterable<User>> getUsers(){
		return userServices.getUsers();
	}
}
