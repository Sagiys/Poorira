package com.hallmann.Poorira.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hallmann.Poorira.entities.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer>{
	
	Optional<Project> findByName(String name);

}
