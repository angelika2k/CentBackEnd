package com.example.demo.repositories;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.usermodel.User;


	@Repository
	public interface UserRepositoy extends MongoRepository<User, Integer> {
		
		Optional<User> findByName(String name);
		
		@Query("{'$or':[ {'title': {$regex : ?0, $options: 'i'}}, {'author': {$regex : ?0, $options: 'i'}}, {'language': {$regex : ?0, $options: 'i'}}, {'genre': {$regex : ?0, $options: 'i'}}]}")
		Page<User> searchBooks(Pageable pageable, String searchText);
}	

