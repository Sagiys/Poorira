package com.hallmann.Poorira.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hallmann.Poorira.DTOs.ProjectDTO;
import com.hallmann.Poorira.additionalLogic.IdentifierGenerator;
import com.hallmann.Poorira.entities.Project;
import com.hallmann.Poorira.entities.Sprint;
import com.hallmann.Poorira.entities.Task;
import com.hallmann.Poorira.entities.User;
import com.hallmann.Poorira.repositories.ProjectRepository;
import com.hallmann.Poorira.repositories.SprintRepository;
import com.hallmann.Poorira.repositories.TaskRepository;
import com.hallmann.Poorira.repositories.UserRepository;

@Service
public class ProjectServices {

	@Autowired
	private ProjectRepository projectRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private SprintRepository sprintRepo;

	@Autowired
	private IdentifierGenerator ig;

	public ResponseEntity<String> createAndAssignProject(Project project, String username) {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		Optional<Project> optionalProject = projectRepo.findByName(project.getName());

		if (optionalProject.isPresent()) {
			return new ResponseEntity<>("Project already exists", HttpStatus.BAD_REQUEST);
		}

		if (!optionalUser.isPresent()) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}

		if (project.getName() == "")
			return new ResponseEntity<>("Project name is empty", HttpStatus.NOT_ACCEPTABLE);

		User user = optionalUser.get();
		project.setOwnerID(user.getID());
		project.setUsers(new ArrayList<User>());
		project.getUsers().add(user);
		project.setNumOfTasks(0);
		project.setIdentifier(ig.generateIdentifier(project.getName()));
		projectRepo.save(project);
		user.getProjects().add(project);
		userRepo.save(user);

		return new ResponseEntity<>("Project Added to user", HttpStatus.OK);
	}
	
	public ResponseEntity<String> editProject(String projectName, ProjectDTO project){
		Optional<Project> optionalProject = projectRepo.findByName(projectName);
		if(!optionalProject.isPresent()) {
			return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
		} else {
			Project oldProject = optionalProject.get();
			oldProject.setName(project.getName());
			String identifier = ig.generateIdentifier(project.getName());
			oldProject.setIdentifier(identifier);
			List<Task> allTasks = new ArrayList<>();
			allTasks.addAll(oldProject.getFinishedTasks());
			allTasks.addAll(oldProject.getUnFinishedTasks());
			allTasks.addAll(oldProject.getUsedTasks());
			for(int i = 0; i < allTasks.size(); i++) {
				StringBuilder sb = new StringBuilder(identifier);
				sb.append("-");
				sb.append(i+1);
				allTasks.get(i).setIdentifier(sb.toString());
				taskRepo.save(allTasks.get(i));
			}
			projectRepo.save(oldProject);
			return new ResponseEntity<>("Project Edited", HttpStatus.OK);
		}
	}

	public ResponseEntity<String> appendUserToProject(String projectName, String username) {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		Optional<Project> optionalProject = projectRepo.findByName(projectName);

		if (!optionalUser.isPresent()) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		} else if (!optionalProject.isPresent()) {
			return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
		} else {
			User user = optionalUser.get();
			Project project = optionalProject.get();
			user.getProjects().add(project);
			project.getUsers().add(user);
			projectRepo.save(project);
			userRepo.save(user);
			return new ResponseEntity<>("Project appended to user", HttpStatus.OK);
		}
	}

	public ResponseEntity<String> deleteProject(String projectName) {
		Optional<Project> optionalProject = projectRepo.findByName(projectName);
		if (!optionalProject.isPresent()) {
			return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
		} else {
			Project project = optionalProject.get();
			List<Task> allTasks = new ArrayList<>();
			allTasks.addAll(project.getFinishedTasks());
			allTasks.addAll(project.getUnFinishedTasks());
			allTasks.addAll(project.getUsedTasks());
			if (allTasks.size() != 0) {
				for (Task t : allTasks) {
					taskRepo.delete(t);
				}
			}
			if(project.getSprints().size() != 0) {
				for(Sprint s : project.getSprints()) {
					sprintRepo.delete(s);
				}
			}
			List<User> users = project.getUsers();
			if (users.size() != 0) {
				for (User u : users) {
					u.getProjects().remove(project);
					userRepo.save(u);
				}
			}

			projectRepo.delete(project);
			return new ResponseEntity<>("Project Deleted", HttpStatus.OK);
		}
	}

	public ResponseEntity<String> deleteUserFromProject(String projectName, String username) {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		Optional<Project> optionalProject = projectRepo.findByName(projectName);

		if (!optionalUser.isPresent()) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		} else if (!optionalProject.isPresent()) {
			return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
		} else {
			User user = optionalUser.get();
			Project project = optionalProject.get();
			project.getUsers().remove(user);
			user.getProjects().remove(project);
			userRepo.save(user);
			projectRepo.save(project);
			return new ResponseEntity<>("User deleted from team", HttpStatus.OK);
		}
	}

	public ResponseEntity<Project> getProject(String projectName) {
		Optional<Project> optionalProject = projectRepo.findByName(projectName);
		if (!optionalProject.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Project project = optionalProject.get();
			return new ResponseEntity<>(project, HttpStatus.OK);
		}
	}

	public ResponseEntity<Iterable<Project>> getProjects() {
		Iterable<Project> projects = projectRepo.findAll();
		if (projects == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(projects, HttpStatus.OK);
	}

}
