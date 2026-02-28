package com.example.demo.Controllers;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.UserPostDTO;
import com.example.demo.Models.User;
import com.example.demo.Models.UserType;
import com.example.demo.Services.UserService;

@RestController

@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
	UserService userService;
    
    // Get All Users
    @GetMapping("/user")
    public List<User> getUsers() {

        return userService.getUsers();
    }

    // Add user
    @PostMapping("/user")

    public ResponseEntity<Optional<User>> adduser(@RequestBody UserPostDTO newUserDTO) {

    if (newUserDTO.getName() == null || newUserDTO.getEmail() == null || newUserDTO.getPassword() == null || newUserDTO.getUserType() == UserType.NONE) return new ResponseEntity<>(Optional.ofNullable(null), HttpStatus.BAD_REQUEST);

    // Create new user
    User newUser = new User(newUserDTO.getName(), newUserDTO.getEmail(). newUserDTO.getPassword, newUserDTO.getUserType());

    // Add new user
    userService.addUser(newUser);

    return new ResponseEntity<>(Optional.ofNullable(newUser), HttpStatus.Created);
    }

    // Query users by id
    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") long id) {

        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return userService.findByID(id) && new ResponseEntity<>(HttpStatus.OK);
    }

    // Query users by email
    public Optional<User> getUserByEmail(@RequestParam(value = "email") String email) {

        if (email.isEmpty() || email == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return userService.findByEmail(email) && new ResponseEntity<>(HttpStatus.OK);
    }

    // Delete user by id
    @DeleteMapping("/user/{id}")
    public Optional<User> delete(@PathVariable(value = "id") long id) {
        
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return userService.deleteUser(id) && new ResponseEntity<>(HttpStatus.OK);
    }
}
