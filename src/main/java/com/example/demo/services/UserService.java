package com.example.demo.services;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.CustomUserRepository;
import com.example.demo.repositories.UserRepositoy;
import com.example.demo.usermodel.User;

@Service

public class UserService {
	@Autowired
	private UserRepositoy userRepo;
	
	@Autowired
	private CustomUserRepository customUserRepository;
	
	public ResponseEntity<User> createUser(User user) {
		try {
			Integer id = customUserRepository.getMaxUserId() + 1;
			User userNew = userRepo.save(new User (id, user.getName(), user.getPhoneNumber()));
			
		    return new ResponseEntity<>(userNew, HttpStatus.CREATED);
		} catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
								
		public ResponseEntity<List<User>> listUsers() {
			try {
			    List<User> users = new ArrayList<User>();
			    userRepo.findAll().forEach(users::add);
			    if (users.isEmpty()) {
			      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			    }
			    return new ResponseEntity<>(users, HttpStatus.OK);
			} catch (Exception e) {
			    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	public ResponseEntity<User> getUserById(Integer id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<Map<String, Object>> getUser(int pageNo, int pageSize, String sortBy) {
		//this method does not work properly
	  try {System.out.println(pageNo);
	  System.out.println(pageSize);
	  System.out.println(sortBy);
	  Map<String, Object> response = new HashMap<>();
	    Sort sort = Sort.by(sortBy);
	    System.out.println(sort);
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		 System.out.println(pageable);
	    Page<User> page = userRepo.findAll(pageable);
	    System.out.println(page);
	    response.put("data", page.getContent());
	    System.out.println(response);
	    response.put("Total no of pages", page.getTotalPages());
	    response.put("Total no of elements", page.getTotalElements());
	    response.put("Current page no", page.getNumber());
		    
		    return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<User> updateUser(Integer id, User user) {
		Optional<User> userData = userRepo.findById(id);

		if (userData.isPresent()) {
			User userOld = userData.get();
			userOld.setName(user.getName());
		    return new ResponseEntity<>(userRepo.save(userOld), HttpStatus.OK);
		} else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<User> deleteUser(int id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			userRepo.delete(user.get());
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<User> getUserByName(String userName) {
		Optional<User> user = userRepo.findByName(userName);
		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
