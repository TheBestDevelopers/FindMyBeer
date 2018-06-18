package com.thebestdevelopers.find_my_beer.controller;

import com.sun.deploy.security.UserDeclinedException;
import com.thebestdevelopers.find_my_beer.DTO.GetUsernameDTO;
import com.thebestdevelopers.find_my_beer.DTO.UserDTO;
import com.thebestdevelopers.find_my_beer.controller.userControllerParam.CreateUserParam;
import com.thebestdevelopers.find_my_beer.controller.userControllerParam.EditUserParam;
import com.thebestdevelopers.find_my_beer.controller.userControllerParam.GetUserParam;
import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import com.thebestdevelopers.find_my_beer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public UserDTO getUser(Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.getUser(user.getUsername());
    }

    @GetMapping("all")
    public List<UserDTO> getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("")
    public UserDTO createUser(@Valid @RequestBody CreateUserParam param) {
        return userService.createUser(param.getUsername(),param.getPassword(), param.getRole());
    }

    @PutMapping("")
    public Boolean changeUserPassword (@Valid @RequestParam(name = "password") String password, @Valid @RequestParam(name = "newPassword") String newPassword, Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.changeUserPassword(user.getUsername(), password, newPassword);
    }

    @DeleteMapping("")
    public Boolean deleteUser(@Valid @RequestParam(name = "password") String password, Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.deleteUser(user.getUsername(), password);
    }

    @GetMapping("getUsername")
    public GetUsernameDTO getUsername(@RequestParam("ID") int id, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.getUsername((int)userEntity.getUserId());
    }

}
