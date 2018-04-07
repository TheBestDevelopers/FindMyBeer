package com.thebestdevelopers.find_my_beer.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.corba.se.spi.ior.ObjectKey;
import com.thebestdevelopers.find_my_beer.DTO.UserDTO;
import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author Dominik Florencki
 */
@RestController
@RequestMapping("api/users/")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public List<UserDTO> getAllUser(){ return userService.getAllUser();}

    @PostMapping("{role}")
    public UserDTO createUser(@Valid @RequestBody UserEntity user, @PathVariable(value = "role") String role) {
        return userService.createUser(user, role);
    }

}
