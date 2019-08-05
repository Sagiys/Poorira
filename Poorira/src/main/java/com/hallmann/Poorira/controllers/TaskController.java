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

import com.hallmann.Poorira.DTOs.CommentDTO;
import com.hallmann.Poorira.DTOs.LogWorkDTO;
import com.hallmann.Poorira.DTOs.TaskDTO;
import com.hallmann.Poorira.entities.Comment;
import com.hallmann.Poorira.entities.LogWork;
import com.hallmann.Poorira.entities.Task;
import com.hallmann.Poorira.services.TaskServices;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/task")
public class TaskController {

	@Autowired
	private TaskServices taskServices;

	@PostMapping(path = "/{projectName}/addtask")
	public ResponseEntity<String> createTaskInProject(@PathVariable String projectName, @RequestBody Task task) {
		return taskServices.createTaskInProject(projectName, task);
	}

	@PostMapping(path = "/{id}/comment/add")
	public ResponseEntity<String> addComment(@RequestBody Comment comment, @PathVariable int id) {
		return taskServices.addComment(comment, id);
	}
	
	@PutMapping(path ="/{commentID}/comment/update")
	public ResponseEntity<String> updateComment(@RequestBody CommentDTO comment, @PathVariable int commentID){
		return taskServices.editComment(comment, commentID);
	}
	
	@PutMapping(path ="/{id}/assign/{username}")
	public ResponseEntity<String> assignPerson(@PathVariable int id, @PathVariable String username){
		return taskServices.assignPerson(id, username);
	}
	
	@PutMapping(path ="/{id}/estimate/{time}")
	public ResponseEntity<String> estimateTask(@PathVariable int id, @PathVariable long time){
		return taskServices.estimateTask(id, time);
	}
	
	@PostMapping(path ="/{id}/work/log")
	public ResponseEntity<String> logWork(@RequestBody LogWork logWork, @PathVariable int id){
		return taskServices.logWork(id, logWork);
	}
	
	@DeleteMapping(path ="/{id}/work/delete")
	public ResponseEntity<String> deleteWork(@PathVariable int id){
		return taskServices.deleteWork(id);
	}
	
	@PutMapping(path = "/{id}/work/update")
	public ResponseEntity<String> updateWork(@RequestBody LogWorkDTO logWork, @PathVariable int id){
		return taskServices.updateWork(logWork, id);
	}
	
	@PutMapping(path ="/{id}/update")
	public ResponseEntity<String> editTask(@RequestBody TaskDTO task, @PathVariable int id){
		return taskServices.editTask(id, task);
	}
	
	@DeleteMapping(path ="/{commentID}/comment/delete")
	public ResponseEntity<String> deleteComment(@PathVariable int commentID){
		return taskServices.deleteComment(commentID);
	}

	@PutMapping(path = "/{id}/description")
	public ResponseEntity<String> editDescription(@RequestBody TaskDTO task, @PathVariable int id){
		return taskServices.editDescription(task, id);
	}
	
	@PutMapping(path ="/{id}/priority/{priority}")
	public ResponseEntity<String> changePriority(@PathVariable int id, @PathVariable int priority){
		return taskServices.changePriority(id, priority);
	}
	
	@DeleteMapping(path ="/{id}/delete")
	public ResponseEntity<String> deleteTask(@PathVariable int id){
		return taskServices.deleteTask(id);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Task> getTask(@PathVariable int id){
		return taskServices.getTask(id);
	}

	@GetMapping(path = "/all")
	public ResponseEntity<Iterable<Task>> getTasks() {
		return taskServices.getTasks();
	}
}