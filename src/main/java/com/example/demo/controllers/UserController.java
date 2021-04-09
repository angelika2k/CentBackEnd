package com.example.demo.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.UserService;
import com.example.demo.usermodel.User;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/users")



public class UserController {
	@Autowired
    private UserService userService;
	
	@PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
		return userService.createUser(user);
	}
    
	
		@GetMapping
    public ResponseEntity<List<User>> listUsers(){
			return userService.listUsers();
	    }
	
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Integer id){
	    return userService.getUserById(id);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Map	<String, Object>> grtUser(@RequestParam(value="pageNo",defaultValue="0") int pageNo,
			@RequestParam(value="pageSize",defaultValue="0") int pageSize,@RequestParam(name = "sortBy", defaultValue = "id") String sortBy){
		return userService.getUser(pageNo,pageSize,sortBy);
	}
	
	@GetMapping(params="name")
	public ResponseEntity<User> getUserByName(@RequestParam String name){
	    return userService.getUserByName(name);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable int id) {
	    return userService.deleteUser(id);
	}
}
