package com.hallmann.Poorira.repositories;

import org.springframework.data.repository.CrudRepository;

import com.hallmann.Poorira.entities.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer>{

}
