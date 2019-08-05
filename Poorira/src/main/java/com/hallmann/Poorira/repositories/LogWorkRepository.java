package com.hallmann.Poorira.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hallmann.Poorira.entities.LogWork;

@Repository
public interface LogWorkRepository extends CrudRepository<LogWork, Integer>{

}
