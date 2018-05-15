package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.service.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
/**
 * @author Jakub Pisula
 * Modified by: Dominik Florencki
 * The Controller to manage the pub account ONLY
 */

@RestController
@RequestMapping("api/pubs/")
public class PubController implements Serializable {
    @Autowired
    PubService pubService;
}
