package com.thebestdevelopers.find_my_beer.service;


import com.thebestdevelopers.find_my_beer.DTO.ConveniencesDTO;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.Result;
import com.thebestdevelopers.find_my_beer.model.ConvenienceTypesEntity;
import com.thebestdevelopers.find_my_beer.model.ConveniencesEntity;
import com.thebestdevelopers.find_my_beer.repository.AddressRepository;
import com.thebestdevelopers.find_my_beer.repository.ConvenienceRepository;
import com.thebestdevelopers.find_my_beer.repository.ConvenienceTypeRepository;
import com.thebestdevelopers.find_my_beer.repository.PubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Grzegorz Nowak
 *
 */
@Service
public class ConveniencesServiceImpl implements ConveniencesService {

    @Autowired
    ConvenienceTypeRepository convenienceTypeRepository;

    @Autowired
    ConvenienceRepository convenienceRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PubRepository pubRepository;

    public ConveniencesDTO getAllConveniences(){
        List<String> conveniencesNames = new ArrayList<>();
        List<ConvenienceTypesEntity> convenienceTypesEntityList = convenienceTypeRepository.findAll();
        convenienceTypesEntityList.forEach(item ->{conveniencesNames.add(item.getDescription());});
        return new ConveniencesDTO(conveniencesNames);
    }

    @Override
    public ConveniencesDTO getPubConveniences(int pubId) {
        List<String> conveniencesNames = new ArrayList<>();
        List<ConveniencesEntity> conveniencesEntityList = convenienceRepository.findByPubId(pubId);
        for (ConveniencesEntity conveniencesEntity : conveniencesEntityList){
            ConvenienceTypesEntity convenienceTypesEntity = convenienceTypeRepository
                    .findByConvenienceTypesId(conveniencesEntity.getConvenienceTypesId()).get(0);
             conveniencesNames.add(convenienceTypesEntity.getDescription());
        }
        return new ConveniencesDTO(conveniencesNames);
    }

    @Override
    public ConveniencesDTO getPubsWithConveniences(Double longitude, Double latitude, String[] conveniences) throws IOException {
        PubDistanceServiceImpl pubDistanceService = new PubDistanceServiceImpl(addressRepository, pubRepository);
        List<Result> results = pubDistanceService.getNearPubs(longitude, latitude);

        return null;
    }


}
