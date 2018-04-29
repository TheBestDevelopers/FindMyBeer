package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.DTO.UserDTO;
import com.thebestdevelopers.find_my_beer.controller.userControllerParam.CreateUserParam;
import com.thebestdevelopers.find_my_beer.controller.userControllerParam.GetUserParam;
import com.thebestdevelopers.find_my_beer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Dominik Florencki
 */
@RestController
@RequestMapping("api/users/")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("")
    public UserDTO getUser(@Valid @RequestBody GetUserParam param){return userService.getUser(param.getUsername(),param.getPassword()); }

    @GetMapping("")
    public List<UserDTO> getAllUser(){ return userService.getAllUser();}

    @PostMapping("new")
    public UserDTO createUser(@Valid @RequestBody CreateUserParam param) {
        return userService.createUser(param.getUsername(),param.getPassword(), param.getRole());
    }

    @DeleteMapping("")
    public Boolean deleteUser(@Valid @RequestBody GetUserParam param){return userService.deleteUser(param.getUsername(), param.getPassword());}

}
