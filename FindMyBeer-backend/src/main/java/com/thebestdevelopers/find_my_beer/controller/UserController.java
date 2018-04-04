package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.model.User;
import com.thebestdevelopers.find_my_beer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Wiktor Florencki
 */
@RestController
@RequestMapping("api/users/")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public List<User> getAllUser(){ return userService.getAllUser();}

    @PostMapping("")
    public User createUser(@Valid @RequestBody User user) {return userService.createUser(user);}
}
