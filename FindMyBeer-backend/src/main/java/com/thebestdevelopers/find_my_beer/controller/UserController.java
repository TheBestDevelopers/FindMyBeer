package com.thebestdevelopers.find_my_beer.controller;

import com.sun.deploy.security.UserDeclinedException;
import com.thebestdevelopers.find_my_beer.DTO.UserDTO;
import com.thebestdevelopers.find_my_beer.controller.userControllerParam.CreateUserParam;
import com.thebestdevelopers.find_my_beer.controller.userControllerParam.EditUserParam;
import com.thebestdevelopers.find_my_beer.controller.userControllerParam.GetUserParam;
import com.thebestdevelopers.find_my_beer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * @author Dominik Florencki
 * The Controller to manage the user account ONLY
 */
@RestController
@RequestMapping("api/users/")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public UserDTO getUser(Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.getUser(user.getUsername());
    }

    @GetMapping("all")
    public List<UserDTO> getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("new")
    public UserDTO createUser(@Valid @RequestBody CreateUserParam param) {
        return userService.createUser(param.getUsername(),param.getPassword(), param.getRole());
    }

    @PutMapping("changeUserPassword")
    public Boolean changeUserPassword (@Valid @RequestBody EditUserParam param, Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        if(!user.getUsername().equals(param.getUsername()))
            throw new UserDeclinedException("You don't have permission");
        return userService.changeUserPassword(param.getUsername(), param.getPassword(), param.getNewPassword());
    }

    @DeleteMapping("")
    public Boolean deleteUser(@Valid @RequestBody GetUserParam param, Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        if(!user.getUsername().equals(param.getUsername()))
            throw new UserDeclinedException("You don't have permission");
        return userService.deleteUser(param.getUsername(), param.getPassword());
    }

}
