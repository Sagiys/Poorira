package com.hallmann.Poorira.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hallmann.Poorira.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	Optional<User> findByUsername(String username);
	
}
