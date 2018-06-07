package com.thebestdevelopers.find_my_beer.service;


import com.thebestdevelopers.find_my_beer.DTO.ConveniencesDTO;
import org.springframework.security.access.prepost.PreAuthorize;


/**
 * @author Grzegorz Nowak
 *
 */
public interface ConveniencesService {
    //@PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    ConveniencesDTO getAllConveniences();
    //@PreAuthorize("hasAnyAuthority('admin','client', 'pub')")
    ConveniencesDTO getPubConveniences(int pubId);
}
