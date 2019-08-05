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

import com.hallmann.Poorira.DTOs.SprintDTO;
import com.hallmann.Poorira.entities.Sprint;
import com.hallmann.Poorira.services.SprintServices;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/sprint")
public class SprintController {

	@Autowired
	private SprintServices sprintServices;

	@PostMapping(path = "/create/{projectName}")
	public ResponseEntity<String> createSprint(@RequestBody Sprint sprint, @PathVariable String projectName) {
		return sprintServices.createSprint(sprint, projectName);
	}

	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<String> deleteSprint(@PathVariable int id) {
		return sprintServices.deleteSprint(id);
	}

	@PutMapping(path = "/finish/{id}")
	public ResponseEntity<String> finishSprint(@PathVariable int id) {
		return sprintServices.endSprint(id);
	}
	
	@PutMapping(path = "/move/{taskID}/to/{sprintID}")
	public ResponseEntity<String> moveTaskToSprint(@PathVariable int taskID, @PathVariable int sprintID){
		return sprintServices.moveTaskToSprint(taskID, sprintID);
	}
	
	@PutMapping(path = "/move/{taskID}/from/{sprintID}")
	public ResponseEntity<String> moveTaskFromSprint(@PathVariable int taskID, @PathVariable int sprintID){
		return sprintServices.moveTaskFromSprint(taskID, sprintID);
	}
	
	@PutMapping(path = "/move/{taskID}/column/{column}")
	public ResponseEntity<String> moveToColumnd(@PathVariable int taskID, @PathVariable int column){
		return sprintServices.moveToColumn(taskID, column);
	}
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<String> updateSprint(@RequestBody SprintDTO sprint, @PathVariable int id){
		return sprintServices.editSprint(sprint, id);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Sprint> getSprint(@PathVariable int id) {
		return sprintServices.getSprint(id);
	}

	@GetMapping(path = "/all")
	public ResponseEntity<Iterable<Sprint>> getSprints() {
		return sprintServices.getSprints();
	}

}
