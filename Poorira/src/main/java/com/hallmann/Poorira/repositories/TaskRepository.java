package com.hallmann.Poorira.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hallmann.Poorira.entities.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer>{

}
