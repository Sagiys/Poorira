package com.hallmann.Poorira.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hallmann.Poorira.DTOs.SprintDTO;
import com.hallmann.Poorira.entities.Project;
import com.hallmann.Poorira.entities.Sprint;
import com.hallmann.Poorira.entities.Task;
import com.hallmann.Poorira.repositories.ProjectRepository;
import com.hallmann.Poorira.repositories.SprintRepository;
import com.hallmann.Poorira.repositories.TaskRepository;

@Service
public class SprintServices {

	@Autowired
	private SprintRepository sprintRepo;

	@Autowired
	private TaskRepository taskRepo;

	@Autowired
	private ProjectRepository projectRepo;

	public ResponseEntity<String> createSprint(Sprint sprint, String projectName) {
		Optional<Project> optionalProject = projectRepo.findByName(projectName);
		if (!optionalProject.isPresent()) {
			return new ResponseEntity<>("Project not Found", HttpStatus.NOT_FOUND);
		} else if (sprint.getName().trim().length() <= 0
				|| Long.toString(sprint.getEstimatedDate()).trim().length() <= 0) {
			return new ResponseEntity<>("Name or estimated date is not typed", HttpStatus.NOT_ACCEPTABLE);
		} else {
			Project project = optionalProject.get();
			for (Sprint s : project.getSprints()) {
				if (s.isActive())
					return new ResponseEntity<>("All sprints must be finished!", HttpStatus.NOT_ACCEPTABLE);
			}
			List<Task> tasksForSprint = new ArrayList<Task>();
			for (Task t : sprint.getUnFinishedTasks()) {
				for (Task tt : project.getUnFinishedTasks()) {
					if (t.getID() == tt.getID()) {
						tasksForSprint.add(t);
					} else {
					}
				}
			}
			sprint.setUnFinishedTasks(tasksForSprint);
			sprint.setEstimatedDate(System.currentTimeMillis() + sprint.getEstimatedDate());
			sprint.setProjectID(project.getID());
			sprintRepo.save(sprint);
			for (Task t : sprint.getUnFinishedTasks()) {
				project.getUsedTasks().add(t);
				t.setSprintID(sprint.getID());
				taskRepo.save(t);
			}
			List<Task> tasksToRemove = new ArrayList<>();
			for(Task t : project.getUnFinishedTasks()) {
				for(Task tt : sprint.getUnFinishedTasks()) {
					if(t.getID() == tt.getID())
						tasksToRemove.add(t);
				}
			}
			for(Task t : tasksToRemove) {
				project.getUnFinishedTasks().remove(t);
			}
			
			project.getSprints().add(sprint);
			projectRepo.save(project);
			return new ResponseEntity<>("Sprint created", HttpStatus.OK);
		}
	}

	public ResponseEntity<String> deleteSprint(int id) {
		Optional<Sprint> optionalSprint = sprintRepo.findById(id);
		if (!optionalSprint.isPresent()) {
			return new ResponseEntity<>("Sprint not found", HttpStatus.NOT_FOUND);
		} else {
			Sprint sprint = optionalSprint.get();
			Optional<Project> optionalProject = projectRepo.findById(sprint.getProjectID());
			if (!optionalProject.isPresent()) {
				return new ResponseEntity<>("Project Not Found", HttpStatus.NOT_FOUND);
			} else {
				Project project = optionalProject.get();
				project.getSprints().remove(sprint);
				projectRepo.save(project);
				sprintRepo.delete(sprint);
				return new ResponseEntity<>("Sprint deleted!", HttpStatus.OK);
			}
		}
	}

	public ResponseEntity<String> editSprint(SprintDTO sprint, int id) {
		Optional<Sprint> optionalSprint = sprintRepo.findById(id);
		if (!optionalSprint.isPresent()) {
			return new ResponseEntity<>("Sprint not found", HttpStatus.NOT_FOUND);
		} else {
			Sprint currentSprint = optionalSprint.get();
			if (sprint.getName() != null)
				currentSprint.setName(sprint.getName());
			if (sprint.getEstimatedDate() != 0)
				currentSprint.setEstimatedDate(currentSprint.getStartDate() + sprint.getEstimatedDate());
			sprintRepo.save(currentSprint);

			return new ResponseEntity<>("Sprint updated", HttpStatus.OK);

		}
	}

	public ResponseEntity<String> endSprint(int id) {
		Optional<Sprint> optionalSprint = sprintRepo.findById(id);
		if (!optionalSprint.isPresent()) {
			return new ResponseEntity<>("Sprint not found", HttpStatus.NOT_FOUND);
		} else {
			Sprint sprint = optionalSprint.get();
			Optional<Project> optionalProject = projectRepo.findById(sprint.getProjectID());
			if (!sprint.isActive()) {
				return new ResponseEntity<>("Sprint is already finished!", HttpStatus.NOT_ACCEPTABLE);
			} else if (!optionalProject.isPresent()) {
				return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
			} else {
				Project project = optionalProject.get();
				sprint.setActive(false);
				sprint.setFinishDate(System.currentTimeMillis());
				for (Task t : sprint.getUnFinishedTasks()) {
					project.getUnFinishedTasks().add(t);
					t.setSprintID(0);

				}
				for (Task t2 : sprint.getUsedTasks()) {
					project.getUnFinishedTasks().add(t2);
					t2.setSprintID(0);
				}
				for(Task t3 : sprint.getFinishedTasks()) {
					project.getFinishedTasks().add(t3);
				}
				project.setUsedTasks(new ArrayList<Task>());
				sprint.setUnFinishedTasks(new ArrayList<Task>());
				sprint.setUsedTasks(new ArrayList<Task>());
				projectRepo.save(project);
				sprintRepo.save(sprint);
				return new ResponseEntity<>("Sprint Ended!", HttpStatus.OK);
			}
		}
	}
	
	public ResponseEntity<String> moveTaskToSprint(int taskID, int sprintID){
		Optional<Task> optionalTask = taskRepo.findById(taskID);
		Optional<Sprint> optionalSprint = sprintRepo.findById(sprintID);
		if(!optionalTask.isPresent() || !optionalSprint.isPresent()) {
			return new ResponseEntity<>("Task or Sprint not found", HttpStatus.NOT_FOUND);
		} else {
			Task task = optionalTask.get();
			Sprint sprint = optionalSprint.get();
			Optional<Project> optionalProject = projectRepo.findById(sprint.getProjectID());
			if(!optionalProject.isPresent()) {
				return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
			}
			Project project = optionalProject.get();
			
			for(Task t : sprint.getUnFinishedTasks()) {
				if(t.getID() == task.getID())
					return new ResponseEntity<>("Task already in Sprint", HttpStatus.NOT_ACCEPTABLE);
			}
			task.setSprintID(sprint.getID());
			sprint.getUnFinishedTasks().add(task);
			
			project.getUsedTasks().add(task);
			project.getUnFinishedTasks().remove(task);
			
			taskRepo.save(task);
			sprintRepo.save(sprint);
			projectRepo.save(project);
			
			return new ResponseEntity<>("Task Moved to Sprint", HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> moveTaskFromSprint(int taskID, int sprintID){
		Optional<Task> optionalTask = taskRepo.findById(taskID);
		Optional<Sprint> optionalSprint = sprintRepo.findById(sprintID);
		if(!optionalTask.isPresent() || !optionalSprint.isPresent()) {
			return new ResponseEntity<>("Task or Sprint not found", HttpStatus.NOT_FOUND);
		} else {
			Task task = optionalTask.get();
			Sprint sprint = optionalSprint.get();
			Optional<Project> optionalProject = projectRepo.findById(sprint.getProjectID());
			if(!optionalProject.isPresent()) {
				return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
			}
			Project project = optionalProject.get();
			
			if(!sprint.getUnFinishedTasks().contains(task) && !sprint.getUsedTasks().contains(task)) {
				return new ResponseEntity<>("Task is not in sprint", HttpStatus.OK);
			}
			
			task.setSprintID(0);
			
			sprint.getUnFinishedTasks().remove(task);
			sprint.getUsedTasks().remove(task);
			
			project.getUnFinishedTasks().add(task);
			project.getUsedTasks().remove(task);
			
			taskRepo.save(task);
			sprintRepo.save(sprint);
			projectRepo.save(project);
		
			return new ResponseEntity<>("Task Moved from Sprint", HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> moveToColumn(int taskID, int column){
		Optional<Task> optionalTask = taskRepo.findById(taskID);
		if(!optionalTask.isPresent()) {
			return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
		} else {
			Task task = optionalTask.get();
			if(task.getSprintID() == 0) {
				return new ResponseEntity<>("Task not in sprint", HttpStatus.NOT_ACCEPTABLE);
			}
			Optional<Sprint> optionalSprint = sprintRepo.findById(task.getSprintID());
			if(!optionalSprint.isPresent()) {
				return new ResponseEntity<>("Sprint not found", HttpStatus.NOT_FOUND);
			}
			Sprint sprint = optionalSprint.get();
			sprint.getUnFinishedTasks().remove(task);
			sprint.getUsedTasks().remove(task);
			sprint.getFinishedTasks().remove(task);
			if(column == 1) {
				sprint.getUnFinishedTasks().add(task);
			} else if (column == 2) {
				sprint.getUsedTasks().add(task);
			} else if(column == 3) {
				sprint.getFinishedTasks().add(task);
			} else {
				return new ResponseEntity<>("Wrong column", HttpStatus.NOT_ACCEPTABLE);
			}
			
			sprintRepo.save(sprint);
			
			return new ResponseEntity<>("Task moved to new Column", HttpStatus.OK);
		}
	}

	public ResponseEntity<Sprint> getSprint(int id) {
		Optional<Sprint> optionalSprint = sprintRepo.findById(id);
		if (!optionalSprint.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(optionalSprint.get(), HttpStatus.OK);
		}
	}

	public ResponseEntity<Iterable<Sprint>> getSprints() {
		Iterable<Sprint> allSprints = sprintRepo.findAll();
		if (allSprints == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(allSprints, HttpStatus.OK);
		}
	}

}
