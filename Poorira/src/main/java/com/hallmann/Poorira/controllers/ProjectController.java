package com.hallmann.Poorira.controllers;

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

import com.hallmann.Poorira.DTOs.ProjectDTO;
import com.hallmann.Poorira.entities.Project;
import com.hallmann.Poorira.services.ProjectServices;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/project")
public class ProjectController {

	@Autowired
	private ProjectServices projectServices;
	
	@PostMapping(path = "/create/{username}")
	public ResponseEntity<String> createProject(@RequestBody Project project, @PathVariable String username){
		return projectServices.createAndAssignProject(project, username);
	}
	
	@PutMapping(path = "/append/{username}/to/{projectName}")
	public ResponseEntity<String> appendUserToProject(@PathVariable String username, @PathVariable String projectName){
		return projectServices.appendUserToProject(projectName, username);
	}
	
	@DeleteMapping(path = "/delete/{projectName}")
	public ResponseEntity<String> deleteProject(@PathVariable String projectName){
		return projectServices.deleteProject(projectName);
	}
	
	@PutMapping(path = "/update/{projectName}")
	public ResponseEntity<String> updateProject(@PathVariable String projectName, @RequestBody ProjectDTO project){
		return projectServices.editProject(projectName, project);
	}
	
	@PutMapping(path = "/remove/{username}/from/{projectName}")
	public ResponseEntity<String> deleteUserFromProject(@PathVariable String username, @PathVariable String projectName){
		return projectServices.deleteUserFromProject(projectName, username);
	}
	
	@GetMapping(path = "/{projectName}")
	public ResponseEntity<Project> getProject(@PathVariable String projectName){
		return projectServices.getProject(projectName);
	}
	
	@GetMapping(path = "/all")
	public ResponseEntity<Iterable<Project>> getProjects(){
		return projectServices.getProjects();
	}
	
}
