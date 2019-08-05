package com.hallmann.Poorira.services;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hallmann.Poorira.DTOs.UserDTO;
import com.hallmann.Poorira.entities.Project;
import com.hallmann.Poorira.entities.User;
import com.hallmann.Poorira.repositories.ProjectRepository;
import com.hallmann.Poorira.repositories.UserRepository;

@Service
public class UserServices implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ProjectRepository projectRepo;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserServices(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public ResponseEntity<String> addNewUser(User user) {
		Optional<User> optionalUser = userRepo.findByUsername(user.getUsername());
		if (optionalUser.isPresent()) {
			return new ResponseEntity<>("User already exists", HttpStatus.FORBIDDEN);
		} else {
			if(user.getUsername().trim().length() <= 0 || user.getPassword().trim().length() <= 0) {
				return new ResponseEntity<>("Username or password is missing", HttpStatus.NOT_ACCEPTABLE);
			}
			User newUser = new User();
			newUser.setUsername(user.getUsername());
			newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userRepo.save(newUser);
			return new ResponseEntity<>("User Created", HttpStatus.OK);
		}

	}
	
	public ResponseEntity<String> editUser(String username, UserDTO user){
		Optional<User> optionalUser = userRepo.findByUsername(username);
		if(!optionalUser.isPresent()) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		} else {
			User oldUser = optionalUser.get();
			oldUser.setUsername(user.getUsername());
			userRepo.save(oldUser);
			return new ResponseEntity<>("User updated", HttpStatus.OK);
		}
	}

	public ResponseEntity<String> deleteUser(String username) {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		if (!optionalUser.isPresent()) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		} else {
			User user = optionalUser.get();
			List<Project> projects = user.getProjects();
			if (projects.size() != 0) {
				for (Project p : projects) {
					p.getUsers().remove(user);
					projectRepo.save(p);
				}
			}

			userRepo.delete(user);
			return new ResponseEntity<>("User: " + user.getUsername() + " deleted!", HttpStatus.OK);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);
		if (user.get() == null) {
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
				user.get().getPassword(), emptyList());
	}

	public ResponseEntity<User> getUser(String username) {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		if (!optionalUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
		}

	}

	public ResponseEntity<List<Project>> getUserProjects(String username) {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		if (!optionalUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(optionalUser.get().getProjects(), HttpStatus.OK);
		}
	}

	public ResponseEntity<Iterable<User>> getUsers() {
		Iterable<User> users = userRepo.findAll();
		if (users == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(users, HttpStatus.OK);
	}

}
