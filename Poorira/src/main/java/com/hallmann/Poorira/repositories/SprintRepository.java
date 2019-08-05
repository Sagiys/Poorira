package com.hallmann.Poorira.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hallmann.Poorira.entities.Sprint;

@Repository
public interface SprintRepository extends CrudRepository<Sprint, Integer>{

}
