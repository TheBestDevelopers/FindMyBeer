package com.thebestdevelopers.find_my_beer.service;


import com.thebestdevelopers.find_my_beer.DTO.ConveniencesDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;


/**
 * @author Grzegorz Nowak
 *
 */
public interface ConveniencesService {
    @PreAuthorize("hasAnyAuthority('admin','client')")
    ConveniencesDTO getAllConveniences();
    @PreAuthorize("hasAnyAuthority('admin','client')")
    ConveniencesDTO getPubConveniences(int pubId);
}
