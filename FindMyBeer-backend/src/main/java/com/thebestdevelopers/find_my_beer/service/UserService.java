package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.model.User;

import java.util.List;

/**
 * @author Wiktor Florencki
 */
public interface UserService {
    List<User> getAllUser();
    User createUser(User user);
}
