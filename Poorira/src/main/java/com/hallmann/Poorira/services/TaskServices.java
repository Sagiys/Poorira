package com.hallmann.Poorira.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hallmann.Poorira.DTOs.CommentDTO;
import com.hallmann.Poorira.DTOs.LogWorkDTO;
import com.hallmann.Poorira.DTOs.TaskDTO;
import com.hallmann.Poorira.entities.Comment;
import com.hallmann.Poorira.entities.LogWork;
import com.hallmann.Poorira.entities.Project;
import com.hallmann.Poorira.entities.Sprint;
import com.hallmann.Poorira.entities.Task;
import com.hallmann.Poorira.entities.User;
import com.hallmann.Poorira.repositories.CommentRepository;
import com.hallmann.Poorira.repositories.LogWorkRepository;
import com.hallmann.Poorira.repositories.ProjectRepository;
import com.hallmann.Poorira.repositories.SprintRepository;
import com.hallmann.Poorira.repositories.TaskRepository;
import com.hallmann.Poorira.repositories.UserRepository;

@Service
public class TaskServices {

	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private LogWorkRepository logWorkRepo;
	
	@Autowired
	private SprintRepository sprintRepo;
	
	public ResponseEntity<String> createTaskInProject(String projectName, Task task){
		Optional<Project> optionalProject = projectRepo.findByName(projectName);
		if(!optionalProject.isPresent()) {
			return new ResponseEntity<>("Project Not Found", HttpStatus.NOT_FOUND);
		} else {
			Project project = optionalProject.get();
			project.setNumOfTasks(project.getNumOfTasks() + 1);
			task.setProjectID(project.getID());
			StringBuilder sb = new StringBuilder(project.getIdentifier());
			sb.append("-");
			sb.append(project.getNumOfTasks());
			task.setIdentifier(sb.toString());
			taskRepo.save(task);
			project.getUnFinishedTasks().add(task);
			projectRepo.save(project);
			return new ResponseEntity<>("Task added Correctly", HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> assignPerson(int id, String username){
		Optional<Task> optionalTask = taskRepo.findById(id);
		Optional<User> optionalUser = userRepo.findByUsername(username);
		
		if(!optionalTask.isPresent()) {
			return new ResponseEntity<>("Task not Found", HttpStatus.NOT_FOUND);
		} else if(!optionalUser.isPresent()) {
			return new ResponseEntity<>("User not Found", HttpStatus.NOT_FOUND);
		} else {
			Task task = optionalTask.get();
			task.setPersonAssigned(username);
			taskRepo.save(task);
			return new ResponseEntity<>("User Assigned to task", HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> logWork(int id, LogWork logWork){
		Optional<Task> optionalTask = taskRepo.findById(id);
		if(!optionalTask.isPresent()) {
			return new ResponseEntity<>("Task not Found", HttpStatus.NOT_FOUND);
		} else {
			Task task = optionalTask.get();
			logWork.setTaskID(id);
			logWorkRepo.save(logWork);
			task.getLogWorks().add(logWork);
			taskRepo.save(task);
			return new ResponseEntity<>("Work Logged", HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> deleteWork(int id){
		Optional<LogWork> optionalLogWork = logWorkRepo.findById(id);
		if(!optionalLogWork.isPresent()) {
			return new ResponseEntity<>("Log Work not found", HttpStatus.NOT_FOUND);
		} else {
			logWorkRepo.delete(optionalLogWork.get());
			return new ResponseEntity<>("Log Work deleted", HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> updateWork(LogWorkDTO logWork, int id){
		Optional<LogWork> optionalLogWork = logWorkRepo.findById(id);
		if(!optionalLogWork.isPresent()) {
			return new ResponseEntity<>("Log Work not found", HttpStatus.NOT_FOUND);
		} else {
			LogWork currentLogWork = optionalLogWork.get();
			if(logWork.getNote() != null)
				currentLogWork.setNote(logWork.getNote());
			if(logWork.getTimeSpent() >= 1)
				currentLogWork.setTimeSpent(logWork.getTimeSpent());
			logWorkRepo.save(currentLogWork);
			return new ResponseEntity<>("LogWork updated", HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> estimateTask(int id, long time){
		Optional<Task> optionalTask = taskRepo.findById(id);
		if(!optionalTask.isPresent()) {
			return new ResponseEntity<>("Task not Found", HttpStatus.NOT_FOUND);
		} else {
			Task task = optionalTask.get();
			task.setEstimatedTime(time);
			taskRepo.save(task);
			return new ResponseEntity<>("Task estimated", HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> changePriority(int id, int priority){
		Optional<Task> optionalTask = taskRepo.findById(id);
		if(!optionalTask.isPresent()) {
			return new ResponseEntity<>("Task not Found", HttpStatus.NOT_FOUND);
		} else {
			if(priority == 0) {
				return new ResponseEntity<>("Priority not given", HttpStatus.NOT_ACCEPTABLE);
			} else {
				Task task = optionalTask.get();
				task.setPriority(priority);
				taskRepo.save(task);
				return new ResponseEntity<>("Task's priority changed", HttpStatus.OK);
			}
		}
	}
	
	public ResponseEntity<String> editDescription(TaskDTO task, int id){
		Optional<Task> optionalTask = taskRepo.findById(id);
		if(!optionalTask.isPresent()) {
			return new ResponseEntity<>("Task not Found", HttpStatus.NOT_FOUND);
		} else {
			Task currentTask = optionalTask.get();
			currentTask.setDescription(task.getDescription());
			taskRepo.save(currentTask);
			return new ResponseEntity<>("Task's description updated", HttpStatus.OK);
		}
	}
	
	
	
	public ResponseEntity<String> addComment(Comment comment, int taskID){
		if(comment.getComment() == "")
			return new ResponseEntity<>("Comment is Empty", HttpStatus.NOT_ACCEPTABLE);
		
		Optional<Task> optionalTask = taskRepo.findById(taskID);
		if(!optionalTask.isPresent())
			return new ResponseEntity<>("Couldn't find task", HttpStatus.NOT_FOUND);
		
		Task task = optionalTask.get();
		commentRepo.save(comment);
		task.getComments().add(comment);
		taskRepo.save(task);
		return new ResponseEntity<>("Comment Added", HttpStatus.OK);
	}
	
	public ResponseEntity<Task> getTask(int id){
		Optional<Task> optionalTask = taskRepo.findById(id);
		if(!optionalTask.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
	}
	
	public ResponseEntity<Iterable<Task>> getTasks(){
		Iterable<Task> tasks = taskRepo.findAll();
		if(tasks == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(tasks, HttpStatus.OK);
	}
	
	public ResponseEntity<String> editTask(int id, TaskDTO task){
		Optional<Task> optionalTask = taskRepo.findById(id);
		if(!optionalTask.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Task currentTask = optionalTask.get();
			if(task.getDescription().trim().length() > 0)
				currentTask.setDescription(task.getDescription());
			if(task.getName().trim().length() > 0)
				currentTask.setName(task.getName());
			taskRepo.save(currentTask);
			return new ResponseEntity<>("Comment Edited!", HttpStatus.OK);
		}
			
	}
	
	public ResponseEntity<String> deleteTask(int id){
		Optional<Task> optionalTask = taskRepo.findById(id);
		if(!optionalTask.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Task task = optionalTask.get();
			Optional<Project> optionalProject = projectRepo.findById(task.getProjectID());
			Optional<Sprint> optionalSprint = sprintRepo.findById(task.getSprintID());
			if(optionalProject.isPresent()) {
				Project project = optionalProject.get();
				project.getUnFinishedTasks().remove(task);
				project.getUsedTasks().remove(task);
				project.getFinishedTasks().remove(task);
				projectRepo.save(project);
			}
			if(optionalSprint.isPresent()) {
				Sprint sprint = optionalSprint.get();
				sprint.getUnFinishedTasks().remove(task);
				sprint.getUsedTasks().remove(task);
				sprint.getFinishedTasks().remove(task);
				sprintRepo.save(sprint);
			}
			if(task.getLogWorks().size() > 0) {
				for(LogWork log : task.getLogWorks()) {
					logWorkRepo.delete(log);
				}				
			}
			if(task.getComments().size() > 0) {
				for(Comment com : task.getComments()) {
					commentRepo.delete(com);
				}
			}
			
			taskRepo.delete(task);
			return new ResponseEntity<>("Task deleted!", HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> deleteComment(int id){
		Optional<Comment> optionalComment = commentRepo.findById(id);
		if(!optionalComment.isPresent())
			return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
		else {
			commentRepo.delete(optionalComment.get());
			return new ResponseEntity<>("Comment has been deleted", HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> editComment(CommentDTO comment, int id){
		Optional<Comment> optionalComment = commentRepo.findById(id);
		if(!optionalComment.isPresent())
			return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
		else {
			Comment currentComment = optionalComment.get();
			currentComment.setComment(comment.getComment());
			commentRepo.save(currentComment);
			return new ResponseEntity<>("Comment has been updated", HttpStatus.OK);
		}
	}
	
}
